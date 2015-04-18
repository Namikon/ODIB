package eu.usrv.odib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import eu.usrv.odib.help.Reference;
import eu.usrv.odib.tabs.ModTabs;

public class BlockBase extends Block {
	  public BlockBase(Material pMaterial) {
		  super(pMaterial);
		  setCreativeTab(ModTabs.tabODIB);
	  }
}
