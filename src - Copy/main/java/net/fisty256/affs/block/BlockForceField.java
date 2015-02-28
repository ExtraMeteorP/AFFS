package net.fisty256.affs.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockForceField extends BlockGlass {
	public BlockForceField(Material materialIn)
	{
		super(materialIn, false);
		this.setBlockUnbreakable();
		this.setResistance(6000001.0F);
	}
	
	public int getRenderType()
	{
		return 3;
	}
	
	public boolean isFullCube()
	{
		return false;
	}
	
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	protected boolean canSilkHarvest()
    {
        return false;
    }
	
	public int quantityDropped(Random random)
	{
		return 0;
	}
}