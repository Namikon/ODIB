package eu.usrv.odib.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import eu.usrv.odib.help.Reference;
import eu.usrv.odib.tabs.ModTabs;

// Dummy block-class to get just a very simple block. Maybe we'll add material propeties later..?
// Like... Radiation from Uranium blocks or such..
public class BlockBase extends Block {
	  public BlockBase(String pBlockName) {
		  super(Material.iron);
		
		  setCreativeTab(ModTabs.tabODIB);
		  setStepSound(soundTypeMetal);
		  setHardness(2.5F);
		  setResistance(10.0F);
		  setHarvestLevel("pickaxe", 1);
		  setBlockName(pBlockName);
		  setBlockTextureName(Reference.MODID + ":" + "Block_" + pBlockName.substring(5)); 
	  }
}
