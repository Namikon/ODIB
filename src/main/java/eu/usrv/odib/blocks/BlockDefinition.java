package eu.usrv.odib.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedInputStream;

import org.apache.commons.lang3.StringUtils;

import eu.usrv.odib.enums.EN_BlockTypes;
import eu.usrv.odib.enums.EN_SoundTypes;
import eu.usrv.odib.enums.EnumTools;
import eu.usrv.odib.gameregistry.PotionIDHelper;
import eu.usrv.odib.help.IntHelper;
import eu.usrv.odib.help.LogHelper;
import eu.usrv.odib.help.Reference;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration;

/**
 * BlockDefinition class. Code-representation of the bconf-files
 * @author Namikon
 */
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
	private String[] _mPotionEffectDefs;
	private Material _mMaterial = null;
	private String _mStrMaterial;
	private List<PotionEffect> _mPotionEffects = new ArrayList<PotionEffect>();
	
	private SoundType _mStepSoundType = null;
	private String _mStepSoundTypeStr;
	
	
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
	public Material getMaterial() { return this._mMaterial; }
	public SoundType getStepSoundType() { return this._mStepSoundType; }
	
	public List<PotionEffect> getPotionEffects() { return this._mPotionEffects; }
	
	public BlockBase getConstructedBlock() { return this._constructedBlock; }
	
	
	/**
	 * @return Some (useless) function to print on the console...
	 */
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
		this._mStepSoundTypeStr = pConfigFile.getString("StepSound", "main", "soundTypeStone", "The sound when any entity is walking over this Block"); 
		
		this._mPotionEffectDefs = pConfigFile.getStringList("PotionEffects", "special", new String[] {}, "The potion ID(s) to apply if any living entity has this Block in his/her inventory. Format to use: <PotionID>|<Duration>|<Level>");
		
		// If, for any reason, the config file is invalid or missing some entries like from older versions,
		// save it now to have it updated
		pConfigFile.save();
		
		// Make sure everything is fine before we add it to our local registry
		if (!CheckValidValues())
			throw new Exception();
	}
	
	/**
	 * Make sure we have a valid block here
	 * @return
	 */
	private boolean CheckValidValues()
	{
		boolean tResult = true;
		if (!CheckNoStringUnchanged())
			tResult = false;

		if (!CheckNoStringWhitespaced())
			tResult = false; 
		
		if (!CheckMaterialIsValid())
			tResult = false;

		if (!CheckStepSoundTypeValid())
			tResult = false;
		
		if (!CheckPotionEffectsValid())
			tResult = false;
		
		return tResult;
	}

	/**
	 * Check potion Effects for syntax and valid IDs. PotionEffect is created once to verify duration and level values, to prevent crashes later while ingame
	 * @return
	 */
	private boolean CheckPotionEffectsValid()
	{
		for (String teffect : _mPotionEffectDefs)
		{
			if (teffect.split("|").length != 3) // invalid syntax
			{
				LogHelper.warn("Invalid PotionEffectString Syntax: " + teffect + " Ignoring entry.");
				return false;
			}

			boolean tParseOk = false;
			if (IntHelper.tryParse(teffect.split("|")[0]))
				if(IntHelper.tryParse(teffect.split("|")[1]))
					if(IntHelper.tryParse(teffect.split("|")[2]))
						tParseOk = true;
					
			if (!tParseOk)
			{
				LogHelper.warn("Potion setting [" + teffect + "] is invalid. Please check your syntax. Ignoring entry");
				return false;
			}
			else
			{
				int tPotionID = Integer.parseInt(teffect.split("|")[0]);
				int tDuration = Integer.parseInt(teffect.split("|")[1]);
				int tLevel = Integer.parseInt(teffect.split("|")[2]);
			
				try
				{
					Potion tP = PotionIDHelper.getPotionIfValid(tPotionID);
					if (tP != null)
					{
						PotionEffect tEff = new PotionEffect(tPotionID, tDuration, tLevel);
						LogHelper.info("Found PotionEffect config; PotionID:[" + tPotionID + "]:[" + tP.getName() + "] Duration:[" + tDuration + "] Level:[" + tLevel + "]");
						_mPotionEffects.add(tEff);
					}
				}
				catch(Exception e)
				{
					LogHelper.warn("Invalid PotionID configuration found. PotionID [" + tPotionID + "] could not be found. Ignoring entry");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Check if given Stepsound is valid. Only vanilla sounds so far. TODO: Add custom sounds
	 * @return
	 */
	private boolean CheckStepSoundTypeValid()
	{
		if (EnumTools.CheckIfStringIsValidEnum(this._mStepSoundTypeStr, EN_SoundTypes.class))
		{
			this._mStepSoundType = EnumTools.ParseSoundTypeClassFromString(this._mStepSoundTypeStr);
			if (this._mStepSoundType == null)
			{
				LogHelper.warn("Something went wrong. SoundType is known but cannot be casted; Please report: [" + this._mStepSoundTypeStr + "]");
				return false;
			}
		} else {
			LogHelper.warn("Block config is invalid. There is no such SoundType: [" + this._mStepSoundTypeStr + "]");
			return false;
		}
		return true;
		
	}
	
	/**
	 * Check if given Material is valid.
	 * @return
	 */
	private boolean CheckMaterialIsValid()
	{
		if (EnumTools.CheckIfStringIsValidEnum(this._mStrMaterial, EN_BlockTypes.class))
		{
			this._mMaterial = EnumTools.ParseMaterialClassFromString(this._mStrMaterial);
			if (this._mMaterial == null)
			{
				LogHelper.warn("Something went wrong. Material is known but cannot be casted; Please report: [" + this._mStrMaterial + "]");
				return false;
			}
		} else {
			LogHelper.warn("Block config is invalid. There is no such Material: [" + this._mStrMaterial + "]");
			return false;
		}
		return true;
	}
	
	/**
	 * Well.. Check for whitespaces obviously
	 * @return
	 */
	private boolean CheckNoStringWhitespaced()
	{
		if (StringUtils.containsWhitespace(this._mName) || StringUtils.containsWhitespace(this._mOreDictName) || StringUtils.containsWhitespace(this._mTextureName) || StringUtils.containsWhitespace(this._mHarvestTool))
			return false;
		else
			return true;
	}
	
	/**
	 * For lazy people who do not read/edit config files
	 * @return
	 */
	private boolean CheckNoStringUnchanged()
	{
		if (this._mName == INVALID_DATA || this._mOreDictName == INVALID_DATA || this._mTextureName == INVALID_DATA || this._mHarvestTool == INVALID_DATA)
			return false;
		else
			return true;
	}
	
	
	/**
	 * Try to construct the block
	 * @return true or false if the construction of the block with given parameters (via config) was successful
	 */
	public boolean ConstructBlock()
	{
		try
		{
			_constructedBlock = new BlockBase(this._mMaterial); 
			
			_constructedBlock.setLightLevel(this._mLightlevel);
			_constructedBlock.setBlockTextureName(Reference.MODID + ":" + this._mTextureName);
			_constructedBlock.setBlockName(this._mName);
			_constructedBlock.setStepSound(_mStepSoundType);
			
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
