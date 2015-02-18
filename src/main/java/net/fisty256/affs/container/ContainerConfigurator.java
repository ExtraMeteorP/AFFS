package net.fisty256.affs.container;

import net.fisty256.affs.gui.SlotConfigurableItem;
import net.fisty256.affs.gui.SlotEnergyStorageItem;
import net.fisty256.affs.gui.SlotLinkCard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerConfigurator extends Container {
	protected InventoryPlayer playerInv;
	protected int inventorySize = 2;
	protected TileEntity te;
	
	public ContainerConfigurator(EntityPlayer player, TileEntity te)
	{
		this.playerInv = player.inventory;
		this.te = te;
		
		addSlotToContainer(new SlotConfigurableItem((IInventory) te, 0, 80, 37));
		addSlotToContainer(new Slot((IInventory) te, 1, 8, 6));
		
		addPlayerSlotsToContainer(playerInv, 8, 84);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
    {
		ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < inventorySize)
            {
                if (!mergeItemStack(itemstack1, inventorySize, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, inventorySize, false))
            {
                return null;
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
	
	public void addPlayerSlotsToContainer(InventoryPlayer inventory, int xOffset, int yOffset) {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			if (i == inventory.currentItem) {
				addSlotToContainer(new Slot(inventory, i, xOffset + i * 18, yOffset + 58));
			} else {
				addSlotToContainer(new Slot(inventory, i, xOffset + i * 18, yOffset + 58));
			}
		}
	}
}