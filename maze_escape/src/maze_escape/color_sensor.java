package maze_escape;


import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class color_sensor {
	
	   private static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
	   private static SensorMode colorMode = colorSensor.getRGBMode();
	   private static float[] colorValue = new float[colorMode.sampleSize()];   
	   final static float RED_THRES = 0.2f;
	   final static float BLACK_THRES_MIN = 0.01f;
	   final static float BLACK_THRES_MAX = 0.04f;
	   final static float BLUE_THRES = 0.1f;
	   final static float YELLOW_THRES = 0.1f;
	   final static float GREEN_THRES = 0.1f;
	   final static int RED = 0;
	   final static int BLACK = 1;
	   final static int BLUE = 2;
	   final static int YELLOW = 3;
	   final static int GREEN = 4;
	

	public static float getColor(){
		colorMode.fetchSample(colorValue, 0);
		   System.out.println(colorValue[0]);
		   System.out.println(colorValue[1]);
		   System.out.println(colorValue[2]);
		   System.out.println("Hello");
	      if (colorValue[0] > YELLOW_THRES && colorValue[1] > YELLOW_THRES) {
	         return YELLOW;
	      }
	      else if (colorValue[0] > RED_THRES) {
	         return RED;
	      }
	      else if (BLACK_THRES_MIN < colorValue[0] && colorValue[0] < BLACK_THRES_MAX 
	            && BLACK_THRES_MIN < colorValue[1] && colorValue[1] < BLACK_THRES_MAX 
	            && BLACK_THRES_MIN < colorValue[2] && colorValue[2] < BLACK_THRES_MAX) {
	         return BLACK;
	      }
	      else if (colorValue[2] > BLUE_THRES) {
	         return BLUE;
	      }
	      else if (colorValue[1] > GREEN_THRES){
	         return GREEN;
	      }
	      else
	         return -1;
	 }
	
		
	public static boolean isBlackBarrier(float color){
		if(color == BLACK){
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
		int i;
		/*for(i=0;i<2;i++){
			wheel_actuator.rotate(27);
			wheel_actuator.forward(0.5);
		}*/
		wheel_actuator.rotate(90);
		save_path.angle +=90;
		save_path.changeDirection(save_path.angle);
		
		//wheel_actuator.forward(0.5);
		wheel_actuator.forward(7.5);
		for (int j=0; j<30; j++)
		save_path.saveToPath(save_path.current_dir);
		
		/*for(i=0;i<2;i++){
			wheel_actuator.rotate(-27);
			wheel_actuator.forward(0.5);
		}*/
		wheel_actuator.rotate(-90);
		save_path.angle -=90;
		save_path.changeDirection(save_path.angle);

		//wheel_actuator.forward(0.5);r
		wheel_actuator.forward(7);
		for (int j=0; j<28; j++)
			save_path.saveToPath(save_path.current_dir);
		
		wheel_actuator.rotate(90);
		save_path.angle +=90;
		save_path.changeDirection(save_path.angle);
		save_path.saveToPath(save_path.current_dir);
		
		
	}

	private static void moveBlue() {
		int i;
		/*for (i=0;i<2;i++){
			wheel_actuator.rotate(-33);
			wheel_actuator.forward(0.5);	
		}*/
		wheel_actuator.rotate(-90);
		save_path.angle -=90;
		save_path.changeDirection(save_path.angle);
		
	//	wheel_actuator.forward(0.5);	
		wheel_actuator.forward(wheel_actuator.one_block);
		save_path.saveToPath(save_path.current_dir);
	}

	private static void moveRed() {
		int i;
	/*	for(i=0;i<3;i++){
			wheel_actuator.rotate(33);
			wheel_actuator.forward(0.5);
		}*/
		wheel_actuator.rotate(90);
		save_path.angle +=90;
		save_path.changeDirection(save_path.angle);

		wheel_actuator.forward(wheel_actuator.one_block);
		save_path.saveToPath(save_path.current_dir);
		
	}
	
		
	
}
