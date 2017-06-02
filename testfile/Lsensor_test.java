package maze_escape;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;

public class Lsensor_test {
	static RegulatedMotor left = Motor.B;
	static RegulatedMotor right = Motor.C;
	static NXTLightSensor light = new NXTLightSensor(SensorPort.S2);
	public static void main(String[] args)  {
		// TODO Auto-generated method stub

	while(true){
		SampleProvider light_itr = light.getAmbientMode();
		float[] lightSample = new float[light_itr.sampleSize()];
		light_itr.fetchSample(lightSample, 0);
		System.out.println(lightSample[0]);
		
		
		try{
			Thread.sleep(1000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		if(lightSample[0]<=0.3){
			
			left.setSpeed(2000);
			right.setSpeed(2000);
			
			left.forward();
			right.forward();
		}
		else
			break;

	}
	}

}
