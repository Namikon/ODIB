package eu.usrv.odib.help;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import eu.usrv.odib.blocks.BlockBase;
import eu.usrv.odib.items.BlockBaseItem;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class ConfigManager {
	private File _mainconfigDir = null;
	private File _blocksconfigDir = null;
	
	private Configuration _mainConfig = null;
	private HashMap<String, BlockDefinition> _customBlocks = new HashMap<String, BlockDefinition>();
	
	FMLPreInitializationEvent _event = null;
	
	 public ConfigManager(FMLPreInitializationEvent pEvent) {
		 _event = pEvent;
	 }
	 
	 public void InitConfigDirs()
	 {
		 LogHelper.info("Checking/creating config folders");
		 
		 // Check/create main config dir /config/ODIB
		 _mainconfigDir = new File(_event.getModConfigurationDirectory(), Reference.MODID);
	    if(!_mainconfigDir.exists()) {
	    	LogHelper.info("Config folder odib/ not found. Creating...");
	    	_mainconfigDir.mkdir();
	    }

	    // Check/create config dir for blocks /config/ODIB/blocks
	    _blocksconfigDir = new File(_mainconfigDir, "blocks");
	    if(!_blocksconfigDir.exists()) {
	    	LogHelper.info("Config folder odib/blocks/ not found. Creating...");
	    	_blocksconfigDir.mkdir();
	    }		    
	 }
	 
	 // register all loaded custom blocks at forge
	 public boolean RegisterBlocks()
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
						 LogHelper.info("Constructed and registered block; " + tEntry.getValue().getBlockInfo());
					 }
					 
				 }
				 catch (Exception e)
				 {
					 LogHelper.error("Could not register custom block; " + tEntry.getValue().getBlockInfo());
					 LogHelper.DumpStack(e);
					 continue;
				 }
			 }
			 return true;
		 }
		 catch (Exception e)
		 {
			 LogHelper.error("Something went (really!) wrong while registering blocks... Please report");
			 LogHelper.DumpStack(e);
			 return false;	
		 }
	 }
	 
	 public boolean LoadBlockConfigs()
	 {
		 LogHelper.info("Block-Definition loading begins --now--");
		 try
		 {
			 // Get all files with extension .bconf > BlockConfig
			 Collection<File> blockFiles = FileUtils.listFiles(_blocksconfigDir, new String[] {"bconf"}, false);
			 LogHelper.info("Found " + blockFiles.size() + " block-definition file(s)");
			 
			 int tNumBlocks = 0;
			 int tNumErrors = 0;
			 
			 for (File tConfig : blockFiles)
			 {
				 try
				 {
					  LogHelper.info("Loading block-definition file: " + tConfig.getName());
					 
				      Configuration tmpCfgManager = new Configuration(tConfig);
				      BlockDefinition tNewBlockDef = new BlockDefinition(tmpCfgManager);
				      
				      if (_customBlocks.containsKey(tNewBlockDef.getName()))
				      {
				    	  LogHelper.warn("Block will be ignored. There is already another Block with this ID: " + tNewBlockDef.getName());
				    	  tNumErrors++;
				    	  continue;
				      }
				      else
				      {
				    	  _customBlocks.put(tNewBlockDef.getName(), tNewBlockDef);
				    	  tNumBlocks++;
				    	  LogHelper.info("New Block successfully loaded: [" + tNewBlockDef.getName() + "] OreDict registration as: [" + tNewBlockDef.getOreDictName() + "]");
				      }
				 }
				 catch (Exception e)
				 {
					 tNumErrors++;
					 LogHelper.error("Unable to load block-definition file: " + tConfig.getName() + ". File is beeing ignored!");
					 continue;
				 }
			 }
			 
			 LogHelper.info("Total blocks loaded: " + tNumBlocks + " Block definitions ignored (due some error): " + tNumErrors);
			 return true;
		 }
		 catch (Exception e)
		 {
			 LogHelper.error("Something went wrong while loading my .bconf files. I'm sorry :(");
			 LogHelper.DumpStack(e);
			 return false;			 
		 }
	 }
}
