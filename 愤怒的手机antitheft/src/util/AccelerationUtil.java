package util;

import android.hardware.SensorManager;

public class AccelerationUtil {
	float fallingValue = 2.0f;
	
	public boolean isFalling(float[] xyz,float previousAcceleration){
		float x =  xyz[SensorManager.DATA_X]; 
			float y = xyz[SensorManager.DATA_Y]; 
	      float z =  xyz[SensorManager.DATA_Z];  
	      double appliedAcceleration = 0.0f;  
	      /* SensorManager.GRAVITY_EARTH = 9.8m/s2 */ 
	      appliedAcceleration +=  Math.pow(x/SensorManager.GRAVITY_EARTH, 2.0); 
	      appliedAcceleration +=  Math.pow(y/SensorManager.GRAVITY_EARTH, 2.0);   
	      appliedAcceleration +=  Math.pow(z/SensorManager.GRAVITY_EARTH, 2.0);   
	      appliedAcceleration = Math.sqrt(appliedAcceleration);  
	      return   ((appliedAcceleration < fallingValue) && (previousAcceleration > fallingValue)); 
	}
	
	
}
