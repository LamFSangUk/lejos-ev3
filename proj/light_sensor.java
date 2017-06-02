package maze_escape;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.utility.Stopwatch;

public class light_sensor {
	 private static boolean outline_flag = false;
	 private static Stopwatch stopwatch = new Stopwatch();//leJOS API
	 private static NXTLightSensor sensor = new NXTLightSensor(SensorPort.S3);//leJOS API
	 private static float[] sample = new float[sensor.getRedMode().sampleSize()];
	 public static float getLight(){
		 sensor.getRedMode().fetchSample(sample, 0);
		 return sample[0];
	 }
	 public static boolean isOnline(){//	  
		 sensor.getRedMode().fetchSample(sample, 0);

		  if(sample[0]<=0.25){//측정해야함
			  outline_flag  = true;
			  return true;
		  } else{
			  outline_flag  = false;
			  return false;
		  }
	  }
	 public static int getTime(){
		 return stopwatch.elapsed();//milli-second
	 }
	  /*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
