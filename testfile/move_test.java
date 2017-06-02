package maze_escape;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
public class move_test {
	static RegulatedMotor left = Motor.B;
	static RegulatedMotor right = Motor.C;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		left.setSpeed(2000);
		right.setSpeed(2000);
		
		left.forward();
		right.forward();
		
		try{
			Thread.sleep(1000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		left.stop();
		right.stop();
	}

}
