package maze_escape;

import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class wheel_actuator {
	static DifferentialPilot pilot = new DifferentialPilot(2.1f,4.4f,Motor.B,Motor.C);
	public static final double one_block = 2;
	static public void set_speed(){
		pilot.setTravelSpeed(5);
	}
	
	static public void forward(double range){
		pilot.travel(range); // 한번에 얼마나 움직일지  mm 단위
	}
	
	static public void backward(double range){
		pilot.travel(-1* range);
	}
	
	static public void stop(){
		pilot.quickStop();
		//System.out.println("Wheel_actuator::stop");
		
	}
	static public void rotate(int angle) {
	    pilot.rotate(angle);
	 }
	 static public void rotateLeft(){
		rotate(-90);
	 }
	 static public void rotateRight(){
		rotate(90);
	 }
}
