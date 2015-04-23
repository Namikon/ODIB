package eu.usrv.odib.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import eu.usrv.odib.help.Reference;
import eu.usrv.odib.tabs.ModTabs;

/**
 * Blockbase. All your blocks belong to us! Super generic block-class
 * @author Namikon
 *
 */
public class BlockBase extends Block {
	  public BlockBase(Material pMaterial) {
		  super(pMaterial);
		  setCreativeTab(ModTabs.tabODIB);
	  }
	  /*
	   *  Particles. Yay!
	  @SideOnly(Side.CLIENT)
	  public void randomDisplayTick(World pWorld, int pX, int pY, int pZ, Random pRnd)
	  {
		  float tfX = (float)pX + 0.5f;
		  float tfY = (float)pY + 1.1f; 
		  float tfZ = (float)pZ + 0.5f;
		  float tf1 = pRnd.nextFloat() * 0.6F - 0.3F;
		  float tf2 = pRnd.nextFloat() * -0.6F - -0.3F;
		  
		  pWorld.spawnParticle("smoke", (double)(tfX+tf1), (double)tfY, (double)(tfZ + tf2), 0.0D, 0.0D, 0.0D);
		  pWorld.spawnParticle("flame", (double)(tfX+tf1), (double)tfY, (double)(tfZ + tf2), 0.0D, 0.0D, 0.0D);
	  }*/
}
