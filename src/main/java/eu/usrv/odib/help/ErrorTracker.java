package eu.usrv.odib.help;

import java.util.ArrayList;

/**
 * A basic errortracker that is used to avoid known loops if a function fails the first time (onUpdate, etc) 
 * @author Namikon
 *
 */
public class ErrorTracker {
	private static ArrayList<String> _mTrackedFunctions = new ArrayList<String>();
	
	
	/**
	 * Check if pFunctionIdentifier had errors before
	 * @param pFunctionIdentifier
	 * @return true/false obviously
	 */
	public static boolean HadErrors(String pFunctionIdentifier)
	{
		if (_mTrackedFunctions.contains(pFunctionIdentifier))
			return true;
		else
			return false;
	}
	
	
	/**
	 * Reset the error-state for pFunctionIdentifier
	 * @param pFunctionIdentifier
	 */
	public static void ClearError(String pFunctionIdentifier)
	{
		if (_mTrackedFunctions.contains(pFunctionIdentifier))
			_mTrackedFunctions.remove(_mTrackedFunctions.indexOf(pFunctionIdentifier));		
	}
	
	
	/**
	 * Reset entire errortracker 
	 */
	public static void Reset()
	{
		_mTrackedFunctions = new ArrayList<String>();
	}
	
	
	/**
	 * Set pFunctionIdentifier as blacklisted function that shall not be called again
	 * @param pFunctionIdentifier
	 */
	public static void TrackError(String pFunctionIdentifier)
	{
		if (!_mTrackedFunctions.contains(pFunctionIdentifier))
			_mTrackedFunctions.add(pFunctionIdentifier);
	}
}
