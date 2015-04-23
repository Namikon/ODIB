package eu.usrv.odib.help;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Register stuff 
 * @author Namikon
 *
 */
public class RegisterHelper {
	
	/**
	 * Register Block without attached itemclass
	 * @param pBlock
	 */
	public static void registerBlock(Block pBlock)
	{
		GameRegistry.registerBlock(pBlock, Reference.MODID + pBlock.getUnlocalizedName().substring(5));
	}
	
	/**
	 * Register Block with attached itemclass
	 * @param pBlock
	 * @param pItemBlock
	 */
	public static void registerBlock(Block pBlock, Class<? extends ItemBlock> pItemBlock)
	{
		GameRegistry.registerBlock(pBlock, pItemBlock, Reference.MODID + pBlock.getUnlocalizedName().substring(5));
	}
}
