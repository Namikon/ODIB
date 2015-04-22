package eu.usrv.odib.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import eu.usrv.odib.help.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockBaseItem extends ItemBlock
{
	private List<PotionEffect> _mPotionEffects = null;
	private String _mBlockName = "";
	
	public BlockBaseItem(Block pParentBlock) {
		super(pParentBlock);
		_mBlockName = pParentBlock.getUnlocalizedName().substring(5);
	}
	
  public void onUpdate(ItemStack pItemStack, World pWorld, Entity pEntity, int pSomeInt, boolean pSomeBoolean)
  {
    super.onUpdate(pItemStack, pWorld, pEntity, pSomeInt, pSomeBoolean);
    
    if ((pEntity instanceof EntityLivingBase)) { // Do we have a living entity?
    	{
    		//((EntityLivingBase)pEntity).addPotionEffect(new PotionEffect(, , ));
      // TODO: Add effects here, depending on some config files
  	  //((EntityLivingBase)pEntity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 3, 10));
    	}
  	  
    }
  }
}
