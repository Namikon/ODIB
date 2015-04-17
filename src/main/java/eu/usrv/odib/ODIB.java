package eu.usrv.odib;

import eu.usrv.odib.blocks.BlocksEnabled;
import eu.usrv.odib.help.*;
import eu.usrv.odib.init.ModBlocks;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ODIB {
	private static Configuration _config = null;
	private static BlocksEnabled _enabledBlocks = null;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		_config = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			_enabledBlocks = new BlocksEnabled(_config);
			ModBlocks.init(_enabledBlocks);
		}
	    catch (Exception e)
	    {
	      FMLLog.log(org.apache.logging.log4j.Level.ERROR, e, "Yeeks, ODIB can't load it's configuration. What did you do??", new Object[0]);
	      FMLLog.severe(e.getMessage(), new Object[0]);
	    }
	    finally
	    {
	      if (_config.hasChanged()) {
	        _config.save();
	      }
	    }
		
	}
	
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{

	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
