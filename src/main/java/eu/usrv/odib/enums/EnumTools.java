package eu.usrv.odib.enums;

import java.lang.reflect.Field;

import eu.usrv.odib.help.LogHelper;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Some basic enum functions
 * @author Namikon
 */
public class EnumTools {
	/**
	 * Check if given string pValue is part of enum pEnum. 
	 * @param pValue String to be checked
	 * @param pEnum EnumClass to be used
	 * @return true / false on match
	 */
	public static <E extends Enum<E>> boolean CheckIfStringIsValidEnum(String pValue, Class<E> pEnum) {
		if (pValue == null || pEnum == null)
			return false;
	  
		for (E e : pEnum.getEnumConstants()) {
			if(e.name().equals(pValue)) 
				return true;
		}
		return false;
	}
	
	
	/**
	 * Parse string to valid SoundType 
	 * @param pValue String representation of SoundType
	 * @return the soundtype or null if it's invalid
	 */
	public static SoundType ParseSoundTypeClassFromString(String pValue)
	{
		try
		{
			Field f = net.minecraft.block.Block.class.getField(pValue);
			if (f != null)
				return (SoundType)f.get(null);
			else
				return null;
		}
		catch(Exception e)
		{
			LogHelper.error("Cannot cast defined SoundType [" + pValue + "] to Minecraft SoundType. Please check your config");
			LogHelper.DumpStack(e);
			return null;
		}
	}
	
	/**
	 * Parse string to valid Material 
	 * @param pValue String representation of Material
	 * @return the Material or null if it's invalid
	 */
	public static Material ParseMaterialClassFromString(String pValue)
	{
		try
		{
			Field f = net.minecraft.block.material.Material.class.getField(pValue);
			if (f != null)
				return (Material)f.get(null);
			else
				return null;
		}
		catch(Exception e)
		{
			LogHelper.error("Cannot cast defined Material [" + pValue + "] to Minecraft Blocktype. Please check your config");
			LogHelper.DumpStack(e);
			return null;
		}
	}
}
