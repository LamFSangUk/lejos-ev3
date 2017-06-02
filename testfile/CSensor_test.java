package maze_escape;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;


public class CSensor_test {
	static EV3ColorSensor color_sensor = new EV3ColorSensor(SensorPort.S3);

	static RegulatedMotor left = Motor.B;
	static RegulatedMotor right = Motor.C;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	

		SampleProvider color_itr = color_sensor.getColorIDMode();
		float[] lightSample = new float[color_itr.sampleSize()];
		color_itr.fetchSample(lightSample, 0);
		System.out.println(lightSample[0]);
		
	//	System.out.println(color_itr);
		
		try{
			Thread.sleep(10000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		if(true){
			
			left.setSpeed(2000);
			right.setSpeed(2000);
			
			left.forward();
			right.forward();
		}
		
	}

}











