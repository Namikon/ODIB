package eu.usrv.odib.gameregistry;

import net.minecraft.potion.Potion;

/**
 * Some basic functions to work with potions
 * @author Namikon
 *
 */
public class PotionIDHelper {
	
	/**
	 * @param pPotionID PotionID to be checked
	 * @return true if given PotionID is valid, false if not
	 */
	public static boolean IsValidPotionID(int pPotionID)
	{
		try
		{
			Potion p = Potion.potionTypes[pPotionID];
			if (p != null)
				return true;
			else
				return false;
		}
		catch(Exception e)
		{
			return false;
		}
	}

 
	/**
	 * Check if the given PotionID is a valid Potion that is registered in the gameregistry
	 * @param pPotionID PotionID to be parsed 
	 * @return Potion to given ID or null if no potion is found
	 */
	public static Potion getPotionIfValid(int pPotionID) {
		if (!IsValidPotionID(pPotionID))
			return null;
		else
			return Potion.potionTypes[pPotionID];
	}
}
