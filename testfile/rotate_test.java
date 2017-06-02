package maze_escape;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

public class rotate_test {
	
	static RegulatedMotor left = Motor.B;
	static RegulatedMotor right = Motor.C;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		left.rotate(-90);
		right.rotate(90);
		try{
			Thread.sleep(100);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
