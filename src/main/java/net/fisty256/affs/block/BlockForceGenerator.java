package net.fisty256.affs.block;

import net.fisty256.affs.AFFS;
import net.fisty256.affs.reference.GUIReferences;
import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockForceGenerator extends BlockContainer {
	public BlockForceGenerator(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityForceGenerator();
	}
	
	public int getRenderType()
	{
		return 3;
	}
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null)
		{
			playerIn.openGui(AFFS.instance, GUIReferences.ID_FORCE_GENERATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		
        return false;
    }
}