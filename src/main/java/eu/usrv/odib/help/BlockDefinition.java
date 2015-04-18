package eu.usrv.odib.help;

import java.util.zip.CheckedInputStream;

import org.apache.commons.lang3.StringUtils;

import eu.usrv.odib.blocks.BlockBase;
import eu.usrv.odib.enums.EN_BlockTypes;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.config.Configuration;

public class BlockDefinition {
	private static final String INVALID_DATA = "YUNOCHANGEME";
	
	private BlockBase _constructedBlock = null;
	
	private String _mName;
	private String _mOreDictName;
	private String _mTextureName;
	private float _mHardness;
	private float _mResistance;
	private int _mHarvestLevel;
	private String _mHarvestTool;
	private boolean _mUnbreakable;
	private float _mLightlevel;
	private int[] _mPotionEffectIDs;
	private Material _mMaterial;
	private String _mStrMaterial;
	
	// Java, y u no support properties o_o
	public String getName() { return this._mName; }
	public String getOreDictName() { return this._mOreDictName; }
	public String getTextureName() { return this._mTextureName; }
	public float getHardness() { return this._mHardness; }
	public float getResistance() { return this._mResistance; }
	public int getHarvestLevel() { return this._mHarvestLevel; }
	public String getHarvestTool() { return this._mHarvestTool; }
	public boolean getUnbreakable() { return this._mUnbreakable; }
	public float getLightlevel() { return this._mLightlevel; }
	public int[] getPotionEffectIDs() { return this._mPotionEffectIDs; }
	public Material getMaterial() { return this._mMaterial; }
	
	public BlockBase getConstructedBlock() { return this._constructedBlock; }
	
	public String getBlockInfo()
	{
		return "Name: [" + this._mName + "] OreDict: [" + this._mOreDictName + "]"; 
	}
	
	public BlockDefinition(Configuration pConfigFile) throws Exception
	{
		this._mName = pConfigFile.getString("Blockname", "main", INVALID_DATA, "Name your block. This must be unique, as it is used to register the block");
		this._mOreDictName = pConfigFile.getString("OreDictName", "main", INVALID_DATA, "How should this block be registered in the ore dictionary? Make sure you follow the oredictionary guidelines at http://www.minecraftforge.net/wiki/Common_Oredict_names");
		this._mTextureName =  pConfigFile.getString("TextureName", "main", INVALID_DATA, "Texture file of your block. Note that Minecraft will search for this file (don't add .png!) in odib:assets/blocks/. See example texturepack-file");
		this._mHardness = pConfigFile.getFloat("Hardness", "main", 2.5F, 1F, 1000F, "The hardness of your block. Higher value means more time to mine it. Stone: 1.5, Obsidian: 50");
		this._mResistance = pConfigFile.getFloat("Resistance", "main", 10F, 1F, 10000F, "The blast-resistance of this block. Stone: 10, Obsidian: 2000");
		this._mHarvestLevel = pConfigFile.getInt("HarvestLevel", "main", 1, 0, 3, "Harvestlevel. 0=Wood, 3=Diamond");
		this._mHarvestTool = pConfigFile.getString("HarvestTool", "main", INVALID_DATA, "The tool to mine it. Valid values: pickaxe, axe, shovel");
		this._mUnbreakable = pConfigFile.getBoolean("Unbreakable", "main", false, "Well... yes... 'Bedrock'");
		this._mLightlevel = pConfigFile.getFloat("Lightlevel", "main", 0F, 0F, 1F, "Glowstone anyone? Float value; Min: 0, Max: 1 (1=Sunlight)");
		this._mStrMaterial = pConfigFile.getString("Material", "main", "iron", "The Material your Block shall be made of. Description is following soon(tm)");

		this._mPotionEffectIDs = pConfigFile.get("special", "PotionIDs", new int[] {}, "The potion ID(s) to apply if any living entity has this Block in his/her inventory").getIntList();
		
		
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
		
		if (this._mName == INVALID_DATA || this._mOreDictName == INVALID_DATA || this._mTextureName == INVALID_DATA || this._mHarvestTool == INVALID_DATA)
			tResult = false;

		if (StringUtils.containsWhitespace(this._mName) || StringUtils.containsWhitespace(this._mOreDictName) || StringUtils.containsWhitespace(this._mTextureName) || StringUtils.containsWhitespace(this._mHarvestTool))
			tResult = false;

		if (EnumTools.CheckIfStringIsValidEnum(this._mStrMaterial, EN_BlockTypes.class))
		{
			this._mMaterial = EnumTools.ParseMaterialClassFromString(this._mStrMaterial);
			if (this._mMaterial == null)
			{
				LogHelper.warn("Something went wrong. Material is known but cannot be casted; Please report: [" + this._mStrMaterial + "]");
				tResult = false;
			}
		} else {
			LogHelper.warn("Block config is invalid. There is no such Material: [" + this._mStrMaterial + "]");
			tResult = false;
		}
		
		return tResult;
	}
	
	
	
	// Construct the block
	public boolean ConstructBlock()
	{
		try
		{
			_constructedBlock = new BlockBase(this._mMaterial); // Iron for now. TODO: make this configureable!
			
			_constructedBlock.setLightLevel(this._mLightlevel);
			_constructedBlock.setBlockTextureName(Reference.MODID + ":" + this._mTextureName);
			_constructedBlock.setBlockName(this._mName);
			
			if (this.getUnbreakable())
				_constructedBlock.setBlockUnbreakable();
			else
			{
				_constructedBlock.setHardness(this._mHardness);
				_constructedBlock.setHarvestLevel(this._mHarvestTool, this._mHarvestLevel);
				_constructedBlock.setResistance(this._mResistance);
			}
			
			return true;
		}
		catch (Exception e)
		{
			LogHelper.error("Unable to construct Block. Please check your block-config file! (Blockname: " + this._mName);
			LogHelper.DumpStack(e);
			return false;
		}
	}
}
