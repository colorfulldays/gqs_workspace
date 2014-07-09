package util;

 

public class LightUtil {
	public static boolean inPocket(float lightValue,float preLightValue){
		 if(lightValue>4&&preLightValue<4){   
		       	return true;
	      } 
        return false; 
	}
}
