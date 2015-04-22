package eu.usrv.odib.enums;

import java.lang.reflect.Field;

import eu.usrv.odib.help.LogHelper;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;

public class EnumTools {
	// Check if given string pValue is part of enum pEnum. Not case sensitive
	public static <E extends Enum<E>> boolean CheckIfStringIsValidEnum(String pValue, Class<E> pEnum) {
		if (pValue == null || pEnum == null)
			return false;
	  
		for (E e : pEnum.getEnumConstants()) {
			if(e.name()/*.toUpperCase()*/.equals(pValue/*.toUpperCase()*/)) // uppercase is bad, since we need exact typing of field values :/
				return true;
		}
		return false;
	}
	
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
	
	// Cast string from config file to MC Material class
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
