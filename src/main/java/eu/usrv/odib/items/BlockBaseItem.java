package eu.usrv.odib.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import eu.usrv.odib.blocks.BlockDefinition;
import eu.usrv.odib.help.ErrorTracker;
import eu.usrv.odib.help.LogHelper;
import eu.usrv.odib.staticregistry.StaticRegistry;
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

/**
 * BlockBaseItem; Class for the "itemized" block that you will pick up when you break it
 * @author Namikon
 */
public class BlockBaseItem extends ItemBlock
{
	private String _mBlockName = "";
	private Random _mRnd = null;
	private BlockDefinition _mBlockDefBuffer = null;
	
	public BlockBaseItem(Block pParentBlock) {
		super(pParentBlock);
		this._mBlockName = pParentBlock.getUnlocalizedName().substring(5);
		this._mRnd = new Random();
	}
	
	
	/**
	 * Get BlockDefinition from local buffer, or if buffer = null, try to get from global buffer.
	 * Also make sure we only try once, to avoid useless lag-loops
	 */
	private BlockDefinition getBlockDefinition()
	{
		if (ErrorTracker.HadErrors("BlockBaseItem.getBlockDefinition." + this._mBlockName))
			return null;
		
		if (this._mBlockDefBuffer != null)
			return this._mBlockDefBuffer;
		else
		{
			this._mBlockDefBuffer = StaticRegistry.getBlockDefByName(this._mBlockName);
			if (this._mBlockDefBuffer == null)
			{
				LogHelper.error("BlockBaseItem.getBlockDefinition.NoBlockDefFound", "There is no BlockDefinition for " + this._mBlockName + " in the local registry. This is bad...");
				ErrorTracker.TrackError("BlockBaseItem.getBlockDefinition." + this._mBlockName);
				return null;
			}
			else
				return this._mBlockDefBuffer;
		}
	}
	
	public void onUpdate(ItemStack pItemStack, World pWorld, Entity pEntity, int pSomeInt, boolean pSomeBoolean)
	{
		super.onUpdate(pItemStack, pWorld, pEntity, pSomeInt, pSomeBoolean);

		if (this._mRnd.nextInt(20) != 0)
			return;

		if (getBlockDefinition() == null)
		{
			LogHelper.warn("BlockBaseItem.onUpdate.NoBlockDefinitionFound", "Skipping entire BlockItem functionality, because the BlockDefinition for " + this._mBlockName + " could not be found");
			return;
		}
		else
		{
			if (this._mBlockDefBuffer.getPotionEffects().size() > 0)
				DoApplyPotions(pEntity);			
		}
	}
	
	
	/**
	 * Try to apply potion(effects) to pEntity
	 * @param pEntity
	 */
	private void DoApplyPotions(Entity pEntity)
	{
		try
		{
			if ((pEntity instanceof EntityLivingBase)) { // Do we have a living entity?
				for (PotionEffect p : this._mBlockDefBuffer.getPotionEffects())
				{
					if (!((EntityLivingBase)pEntity).isPotionActive(p.getPotionID())) // only apply effect when it's not already there
					{
						((EntityLivingBase)pEntity).addPotionEffect(p);
					}
				}
			}
		}
		catch (Exception e)
		{
			LogHelper.error("BlockBaseItem.DoApplyPotions.ErrorOnApplyEffect", "Unable to add potionEffect. Please report this error. It will not show up again, but it will probably keep crashing!");
			LogHelper.DumpStack("BlockBaseItem.DoApplyPotions.ErrorOnApplyEffect.StackTrace", e);
		}
	}
	
	
}
