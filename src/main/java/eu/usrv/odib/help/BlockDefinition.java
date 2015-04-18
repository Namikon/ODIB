package eu.usrv.odib.help;

import org.apache.commons.lang3.StringUtils;

import eu.usrv.odib.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.config.Configuration;

public class BlockDefinition {
	private static final String INVALID_DATA = "YUNOCHANGEME";
	
	private BlockBase _constructedBlock = null;
	
	private String Name;
	private String OreDictName;
	private String TextureName;
	private float Hardness;
	private float Resistance;
	private int HarvestLevel;
	private String HarvestTool;
	private boolean Unbreakable;
	private float Lightlevel;
	
	// Java, y u no support properties o_o
	public String getName() { return this.Name; }
	public String getOreDictName() { return this.OreDictName; }
	public String getTextureName() { return this.TextureName; }
	public float getHardness() { return this.Hardness; }
	public float getResistance() { return this.Resistance; }
	public int getHarvestLevel() { return this.HarvestLevel; }
	public String getHarvestTool() { return this.HarvestTool; }
	public boolean getUnbreakable() { return this.Unbreakable; }
	public float getLightlevel() { return this.Lightlevel; }
	public BlockBase getConstructedBlock() { return this._constructedBlock; }
	
	public String getBlockInfo()
	{
		return "Name: [" + this.Name + "] OreDict: [" + this.OreDictName + "]"; 
	}
	
	public BlockDefinition(Configuration pConfigFile) throws Exception
	{
		this.Name = pConfigFile.getString("Blockname", "main", INVALID_DATA, "Name your block. This must be unique, as it is used to register the block");
		this.OreDictName = pConfigFile.getString("OreDictName", "main", INVALID_DATA, "How should this block be registered in the ore dictionary? Make sure you follow the oredictionary guidelines at http://www.minecraftforge.net/wiki/Common_Oredict_names");
		this.TextureName =  pConfigFile.getString("TextureName", "main", INVALID_DATA, "Texture file of your block. Note that Minecraft will search for this file (don't add .png!) in odib:assets/blocks/. See example texturepack-file");
		this.Hardness = pConfigFile.getFloat("Hardness", "main", 2.5F, 1F, 1000F, "The hardness of your block. Higher value means more time to mine it. Stone: 1.5, Obsidian: 50");
		this.Resistance = pConfigFile.getFloat("Resistance", "main", 10F, 1F, 10000F, "The blast-resistance of this block. Stone: 10, Obsidian: 2000");
		this.HarvestLevel = pConfigFile.getInt("HarvestLevel", "main", 1, 0, 3, "Harvestlevel. 0=Wood, 3=Diamond");
		this.HarvestTool = pConfigFile.getString("HarvestTool", "main", INVALID_DATA, "The tool to mine it. Valid values: pickaxe, axe, shovel");
		this.Unbreakable = pConfigFile.getBoolean("Unbreakable", "main", false, "Well... yes... 'Bedrock'");
		this.Lightlevel = pConfigFile.getFloat("Lightlevel", "main", 0F, 0F, 1F, "Glowstone anyone? Float value; Min: 0, Max: 1 (1=Sunlight)");

		// If, for any reason, the config file is invalid, save the default values to people get a correct one
		pConfigFile.save();
		
		if (!CheckValidValues())
			throw new Exception();
	}
	
	// Make sure we have a valid block here
	// - No whitespaces
	// - All values "no default"
	// - Some more failchecks to be added soon (tm)
	private boolean CheckValidValues()
	{
		boolean tResult = true;
		
		if (this.Name == INVALID_DATA || this.OreDictName == INVALID_DATA || this.TextureName == INVALID_DATA || this.HarvestTool == INVALID_DATA)
			tResult = false;

		if (StringUtils.containsWhitespace(this.Name) || StringUtils.containsWhitespace(this.OreDictName) || StringUtils.containsWhitespace(this.TextureName) || StringUtils.containsWhitespace(this.HarvestTool))
			tResult = false;

		return tResult;
	}
	
	// Construct the block
	public boolean ConstructBlock()
	{
		try
		{
			_constructedBlock = new BlockBase(Material.iron); // Iron for now. TODO: make this configureable!
			
			_constructedBlock.setLightLevel(this.Lightlevel);
			_constructedBlock.setBlockTextureName(Reference.MODID + ":" + this.TextureName);
			_constructedBlock.setBlockName(this.Name);
			
			if (this.getUnbreakable())
				_constructedBlock.setBlockUnbreakable();
			else
			{
				_constructedBlock.setHardness(this.Hardness);
				_constructedBlock.setHarvestLevel(this.HarvestTool, this.HarvestLevel);
				_constructedBlock.setResistance(this.Resistance);
			}
			
			return true;
		}
		catch (Exception e)
		{
			LogHelper.error("Unable to construct Block. Please check your block-config file! (Blockname: " + this.Name);
			LogHelper.DumpStack(e);
			return false;
		}
	}
}
