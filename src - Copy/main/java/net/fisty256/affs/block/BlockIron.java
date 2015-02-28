package net.fisty256.affs.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockIron extends Block {
	public BlockIron() {
		super(Material.iron);
		this.setHardness(7f);
		this.setResistance(200f);
	}
}