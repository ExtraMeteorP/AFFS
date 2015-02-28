package net.fisty256.affs.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSteel extends Block {
	public BlockSteel() {
		super(Material.iron);
		this.setHardness(15f);
		this.setResistance(1000f);
	}
}