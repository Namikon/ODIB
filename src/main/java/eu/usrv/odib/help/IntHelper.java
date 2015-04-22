package eu.usrv.odib.help;

public class IntHelper {
	// Java.. y u no support tryparse... 
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
