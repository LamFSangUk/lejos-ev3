package maze_escape;

public class line_tracer {
	static public int direction;
	static public final int RIGHT = 1;
	static public final int LEFT = -1;
	
	private boolean wheel_init = false;
	public line_tracer(){
		init();
	}
	public boolean isBlack(){
		if(light_sensor.getLight() <= 0.25)
			return true;
		else 
			return false;
	}
	private void init() {
		// TODO Auto-generated method stub
		direction = RIGHT;
		if(!wheel_init){
			wheel_init = true;
			wheel_actuator.set_speed();
		}
		
	}
	
	
}
