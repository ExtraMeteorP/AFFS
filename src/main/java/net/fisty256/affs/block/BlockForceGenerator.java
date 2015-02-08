package net.fisty256.affs.block;

import net.fisty256.affs.tileentity.TileEntityForceGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
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
}