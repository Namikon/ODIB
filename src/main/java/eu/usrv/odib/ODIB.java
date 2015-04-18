package eu.usrv.odib;

import java.io.File;

import eu.usrv.odib.blocks.BlocksEnabled;
import eu.usrv.odib.help.*;
//import eu.usrv.odib.init.ModBlocks;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ODIB {
	private static ConfigManager _cfgManager = null;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		try 
		{
			_cfgManager = new ConfigManager(event);
			_cfgManager.InitConfigDirs();
			_cfgManager.LoadBlockConfigs();
		}
	    catch (Exception e)
	    {
	    	LogHelper.error("Yeeks, ODIB can't load it's configuration. What did you do??");
	    	LogHelper.DumpStack(e);
	    }
		
		try 
		{
			_cfgManager.RegisterBlocks();
			//ModBlocks.init(_enabledBlocks);
		}
	    catch (Exception e)
	    {
	    	LogHelper.error("Something went wrong while registering blocks. I'm sorry :(");
	    	LogHelper.DumpStack(e);
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
