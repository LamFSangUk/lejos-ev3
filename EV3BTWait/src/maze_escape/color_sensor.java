package maze_escape;


import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class color_sensor {
	
	
	static EV3ColorSensor color_sensor = new EV3ColorSensor(SensorPort.S3);

	public static float getColor(){
		SampleProvider color_itr = color_sensor.getColorIDMode();
		float[] lightSample = new float[color_itr.sampleSize()];
		color_itr.fetchSample(lightSample, 0);
		System.out.println(lightSample[0]);
		
		return lightSample[0];
	}
		
	public static boolean isBlackBarrier(float color){
		if(color == 7){
			return true;
		}
		return false;
	}
	
	public static void getColorMove(float color){
		if(color == 0){
			moveRed();
			
		}
		else if(color == 2){
			moveBlue();
		}
		else if(color == 3){
			moveYellow();
			
		}
		else{//different color
			wheel_actuator.stop();
			
		}
		
	}

	private static void moveYellow() {
		// TODO Auto-generated method stub
		int i;
		for(i=0;i<2;i++){
			wheel_actuator.rotate(27);
			wheel_actuator.forward(0.5);
		}
		wheel_actuator.rotate(32);
		save_path.angle +=86;
		save_path.changeDirection(save_path.angle);
		
		wheel_actuator.forward(0.5);
		wheel_actuator.forward(6);
		save_path.saveToPath(save_path.current_dir);
		save_path.saveToPath(save_path.current_dir);
		save_path.saveToPath(save_path.current_dir);
		
		for(i=0;i<2;i++){
			wheel_actuator.rotate(-27);
			wheel_actuator.forward(0.5);
		}
		wheel_actuator.rotate(-20);
		save_path.angle -=74;
		save_path.changeDirection(save_path.angle);

		wheel_actuator.forward(0.5);
		wheel_actuator.forward(4);
		save_path.saveToPath(save_path.current_dir);
		save_path.saveToPath(save_path.current_dir);
	}

	private static void moveBlue() {
		// TODO Auto-generated method stub
		int i;
		for (i=0;i<2;i++){
			wheel_actuator.rotate(-33);
			wheel_actuator.forward(0.5);	
		}
		wheel_actuator.rotate(-34);
		save_path.angle -=100;
		save_path.changeDirection(save_path.angle);
		
		wheel_actuator.forward(0.5);	
		wheel_actuator.forward(wheel_actuator.one_block);
		save_path.saveToPath(save_path.current_dir);
	}

	private static void moveRed() {
		// TODO Auto-generated method stub
		int i;
		for(i=0;i<3;i++){
			wheel_actuator.rotate(27);
			wheel_actuator.forward(0.5);
		}
		save_path.angle +=81;
		save_path.changeDirection(save_path.angle);

		wheel_actuator.forward(wheel_actuator.one_block);
		save_path.saveToPath(save_path.current_dir);
		
	}
	
		
	
}
