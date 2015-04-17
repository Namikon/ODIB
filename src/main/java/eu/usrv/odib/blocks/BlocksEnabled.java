package eu.usrv.odib.blocks;

import java.util.ArrayList;
import java.util.List;

import eu.usrv.odib.help.EBlockTypes;
import net.minecraftforge.common.config.Configuration;

public class BlocksEnabled
{
	private List<String> mEnabledBlocks = new ArrayList<String>();
	private Configuration _cfg = null;
	
	public BlocksEnabled(Configuration pConfigManager)
	{
		_cfg = pConfigManager;
		LoadBlockConfig();
	}
	
	// Check if blocktype from enum is enabled or not
	public boolean IsBlockEnabled(EBlockTypes pType)
	{
		if(mEnabledBlocks.contains(pType.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	// Load config file.
	// If no config file is present, all blocks default to false
	private void LoadBlockConfig()
	{
		for (EBlockTypes bType : EBlockTypes.values())
		{
			if (_cfg.get("enabledBlocks", bType.toString(), false).getBoolean(false)) {
				mEnabledBlocks.add(bType.toString());
			}
		}
		// We're done loading here, now save the config so any new values are saved
		_cfg.save();
	}
}
