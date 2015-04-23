package eu.usrv.odib.help;

/**
 * Java.. y u no support tryparse...
 * @author Namikon
 *
 */
public class IntHelper {

	/**
	 * Seriously.. no tryparse and no byreference calls, what is this, dos-batch?
	 * @param pValue
	 * @return
	 */
	public static boolean tryParse(String pValue)  
	{  
	     try  
	     {  
	         Integer.parseInt(pValue);  
	         return true;  
	      } catch(NumberFormatException nfe)  
	      {  
	          return false;  
	      }  
	}
}
