package eu.usrv.odib.staticregistry;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraftforge.oredict.OreDictionary;
import eu.usrv.odib.blocks.BlockDefinition;
import eu.usrv.odib.help.LogHelper;
import eu.usrv.odib.help.RegisterHelper;
import eu.usrv.odib.items.BlockBaseItem;

/**
 * Static, local registry of Blocks/Items and all stuff that needs to be stored somewhere to access it anytime
 * @author Namikon
 */
public final class StaticRegistry {
	private static HashMap<String, BlockDefinition> _customBlocks = new HashMap<String, BlockDefinition>();
	
	/**
	 * Checks if a given BlockDefinition is already registered in our local registry
	 * @param pBlockDef The BlockDefinition to check
	 * @return true if a blockdefinition is found, false if not
	 */
	public static boolean doesBlockDefExists(BlockDefinition pBlockDef)
	{
		return _customBlocks.containsKey(pBlockDef.getName()) ? true : false;
	}

	/**
	 * Checks if a given BlockDefinition-Name is already registered in our local registry
	 * @param pBlockDefString The name to check
	 * @return true if a blockdefinition is found, false if not
	 */
	public static boolean doesBlockDefExists(String pBlockDefString)
	{
		return _customBlocks.containsKey(pBlockDefString) ? true : false;
	}
	
	// Add new block to the local registry for reference
	/**
	 * Adds a new BlockDefinition to the local static registry. Checks for double-names
	 * @param pBlockDef The BlockDefinition which shall be stored in our static registry
	 * @return true if the block could be added, false if not.
	 */
	public static boolean addBlockDefToRegistry(BlockDefinition pBlockDef)
	{
		try
		{
			_customBlocks.put(pBlockDef.getName(),  pBlockDef);
			
			return true;
		}
		catch (Exception e)
		{
			LogHelper.error("[ODIB Registry] Unable to add BlockDefinition to local registry");
			LogHelper.DumpStack(e);
			return false;
		}
	}
	
	
	/**
	 * Returns the instance of a custom block, by providing its name
	 * @param pBlockName The name of the block that shall be returned
	 * @return Instance of BlockDefinition or null if it can't be found
	 */
	public static BlockDefinition getBlockDefByName(String pBlockName)
	{
		try
		{
			if (!doesBlockDefExists(pBlockName))
				return null;
			else
				return _customBlocks.get(pBlockName);
		}
		catch (Exception e)
		{
			LogHelper.error("[ODIB Registry] Something went wrong on the registry. Please report this on github");
			LogHelper.DumpStack(e);
			return null;
		}
	}
	
	

	 /**
	  * Tries to construct the custom blocks and registers them in the game registry. Any Blocks that can't be constructed
	  * or registered, will be skipped
	 * @return true / false depending on success
	 */
	public static boolean RegisterBlocks()
	 {
		 try
		 {
			 for (Entry<String, BlockDefinition> tEntry : _customBlocks.entrySet())
			 {
				 try
				 {
					 if (tEntry.getValue().ConstructBlock())
					 {
						 RegisterHelper.registerBlock(tEntry.getValue().getConstructedBlock(), BlockBaseItem.class);
						 OreDictionary.registerOre(tEntry.getValue().getOreDictName(), tEntry.getValue().getConstructedBlock());
						 LogHelper.info("[ODIB Registry] Constructed and registered block; " + tEntry.getValue().getBlockInfo());
					 }
					 
				 }
				 catch (Exception e)
				 {
					 LogHelper.error("[ODIB Registry] Could not register custom block; " + tEntry.getValue().getBlockInfo());
					 LogHelper.DumpStack(e);
					 continue;
				 }
			 }
			 return true;
		 }
		 catch (Exception e)
		 {
			 LogHelper.error("[ODIB Registry] Something went (really!) wrong while registering blocks... Please report");
			 LogHelper.DumpStack(e);
			 return false;	
		 }
	 }
}
