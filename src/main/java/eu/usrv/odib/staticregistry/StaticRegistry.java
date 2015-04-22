package eu.usrv.odib.staticregistry;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraftforge.oredict.OreDictionary;
import eu.usrv.odib.blocks.BlockDefinition;
import eu.usrv.odib.help.LogHelper;
import eu.usrv.odib.help.RegisterHelper;
import eu.usrv.odib.items.BlockBaseItem;

public final class StaticRegistry {
	private static HashMap<String, BlockDefinition> _customBlocks = new HashMap<String, BlockDefinition>();
	
	// Check if another block is already registered with a similar name
	public static boolean doesBlockDefExists(BlockDefinition pBlockDef)
	{
		return _customBlocks.containsKey(pBlockDef.getName()) ? true : false;
	}

	// Check if another block is already registered with a similar name
	public static boolean doesBlockDefExists(String pBlockDefString)
	{
		return _customBlocks.containsKey(pBlockDefString) ? true : false;
	}
	
	// Add new block to the local registry for reference
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
	
	// get Blockdefinition by its name
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
	
	
	 // register all loaded custom blocks at forge
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
