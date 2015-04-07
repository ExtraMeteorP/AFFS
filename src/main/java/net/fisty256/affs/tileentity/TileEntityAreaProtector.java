package net.fisty256.affs.tileentity;

import java.util.List;

import net.fisty256.affs.init.ItemsAFFS;
import net.fisty256.affs.item.ItemConfigurable;
import net.fisty256.affs.item.ItemWhitelistCard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class TileEntityAreaProtector extends TileEntityForceManager implements IInventory, IUpdatePlayerListBox {
	public Container container;
	protected ItemStack[] content = new ItemStack[5];
	
	private static final int SLOT_LINKCARD = 0;
	private static final int SLOT_RADIUS = 1;
	private static final int SLOT_WHITELIST = 2;
	
	private static final int WHITELIST_COUNT = 3;
	private static final int BASE_RADIUS = 6;
	private static final int FORCE_MULTIPLIER = 10;
	private static final int FORCE_HIT_MULTIPLIER = 2;
	
	public int currentRadius = 0;
	public int currentForceUsage = 0;
	public int forceUsagePerHit = 0;
	public boolean isRunning = false;
	
	@Override
	public void update()
	{
		if (getStackInSlot(SLOT_RADIUS) != null && getStackInSlot(SLOT_RADIUS).getItem() == ItemsAFFS.radius_upgrade)
		{
			this.currentRadius = BASE_RADIUS + getStackInSlot(SLOT_RADIUS).stackSize;
		}
		else
		{
			this.currentRadius = BASE_RADIUS;
		}
		
		if (isRunning)
		{
			currentForceUsage = currentRadius * FORCE_MULTIPLIER;
			forceUsagePerHit = currentForceUsage * FORCE_HIT_MULTIPLIER;
		}
		else
		{
			currentForceUsage = 0;
			forceUsagePerHit = 0;
		}
		
		if (worldObj.isBlockPowered(this.getPos()))
		{
			if (getForceAmount(getStackInSlot(SLOT_LINKCARD)) >= currentRadius * FORCE_MULTIPLIER)
			{
				isRunning = true;
			}
			else
			{
				isRunning = false;
				return;
			}
		}
		else
		{
			isRunning = false;
			return;
		}
		
		if (!worldObj.isRemote) //Server update
		{
			if (isRunning)
			{
				decreaseForceAmount(getStackInSlot(SLOT_LINKCARD), currentForceUsage);
				
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(getPos().getX()-currentRadius, getPos().getY()-currentRadius, getPos().getZ()-currentRadius,
						getPos().getX()+currentRadius, getPos().getY()+currentRadius, getPos().getZ()+currentRadius));
				
				for (EntityPlayer player : players)
				{
					if (shouldBeKilled(player.getUniqueID().toString()))
					{
						if (getForceAmount(getStackInSlot(SLOT_LINKCARD)) >= forceUsagePerHit)
						{
							player.attackEntityFrom(new DamageSource("affs.areaProtector"), 4f);
							decreaseForceAmount(getStackInSlot(SLOT_LINKCARD), forceUsagePerHit);
						}
					}
				}
			}
		}
	}
	
	public boolean shouldBeKilled(String uuid)
	{
		for (int i = SLOT_WHITELIST; i < SLOT_WHITELIST+WHITELIST_COUNT; i++)
		{
			if (ItemWhitelistCard.isValid(getStackInSlot(i)))
			{
				if (ItemWhitelistCard.getPlayerUUID(getStackInSlot(i)).equals(uuid))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public String getName() {
		return "Area Protector";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return content.length;
	}

	@Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return content[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            }
            else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (content[slotIndex] != null)
        {
            ItemStack itemStack = content[slotIndex];
            content[slotIndex] = null;
            return itemStack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
    	content[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < content.length; ++currentIndex)
        {
            if (content[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                content[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbt.setTag("InventoryContent", tagList);
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		NBTTagList tagList = nbt.getTagList("InventoryContent", 10);
		content = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < content.length)
            {
            	content[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
	}
}