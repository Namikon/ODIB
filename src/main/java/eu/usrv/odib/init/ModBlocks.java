/*package eu.usrv.odib.init;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import eu.usrv.odib.blocks.*;
import eu.usrv.odib.help.EBlockTypes;
import eu.usrv.odib.help.RegisterHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {

	private static BlocksEnabled _enabledBlocks = null;
	
    public static void init(BlocksEnabled pEnabledBlocks)
    {
    	_enabledBlocks = pEnabledBlocks;
    	InitBlocks();
    }
    
    private static void InitBlocks()
    {
    	// Now go ahead and register all blocks that have been enabled in our config file
    	for (EBlockTypes eType : EBlockTypes.values())
    	{
    		if (_enabledBlocks.IsBlockEnabled(eType))
    		{
    			FMLLog.log(org.apache.logging.log4j.Level.DEBUG, eType + " is enabled. Registering...");
    			try
    			{
	    			String tBlockName = "block" + eType.toString();
	    			BlockBase tBlock = new BlockBase(tBlockName);
	    			RegisterHelper.registerBlock(tBlock);
	    			OreDictionary.registerOre(tBlockName, tBlock);
    			}
    			catch (Exception e)
    			{
    				FMLLog.log(org.apache.logging.log4j.Level.ERROR, e, eType + " could not be registered", new Object[0]);	
    			}
    		}
    	}
    }
}*/
