package maze_escape;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.utility.Stopwatch;

public class light_sensor {
	 private static Stopwatch stopwatch = new Stopwatch();//leJOS API
	 private static NXTLightSensor sensor = new NXTLightSensor(SensorPort.S2);//leJOS API
	 private static float[] sample = new float[sensor.getRedMode().sampleSize()];
	 
	 public static float getLight(){
		 sensor.getRedMode().fetchSample(sample, 0);
		 return sample[0];
	 }

	 public static int getTime(){
		 return stopwatch.elapsed();//milli-second
	 }

}
