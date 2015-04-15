package eu.usrv.odib.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.usrv.odib.help.Reference;
import eu.usrv.odib.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Blocks_A extends Block {
	  @SideOnly(Side.CLIENT)
	  private IIcon[] texture;
	
	  public Blocks_A() {
		  super(Material.iron);
		
		  setCreativeTab(ModTabs.tabODIB);
		  setStepSound(soundTypeMetal);
		  setHardness(2.5F);
		  setResistance(10.0F);
		  setHarvestLevel("pickaxe", 1);
		  //setBlockName(pBlockName);
	  //setBlockTextureName(Reference.MODID + ":" + pBlockName + "_" + );
	  }

	public static final String[] subBlocks = { "Adamantium", "Aluminium", "Americium", "AnnealedCopper", "Antimony", "AstralSilver"};
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		   this.texture = new IIcon[subBlocks.length];
		    for (int i = 0; i < subBlocks.length; i++) {
		      this.texture[i] = reg.registerIcon("Ztones".toLowerCase() + ":" + "sets/zone/zone_" + subBlocks[i]);
		    }
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs tab, List subItems)
	{
		for (int i = 0; i < subBlocks.length; i++) {
			subItems.add(new ItemStack(block, 1, i));
		}
	}
	
	  @SideOnly(Side.CLIENT)
	  public IIcon getBlockTextureFromSideAndMetadata (int side, int meta)
	  {
	    return this.texture[meta];
	  }
	
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}

