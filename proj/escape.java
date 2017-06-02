package maze_escape;

public class escape {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int mia = 0;
		line_tracer tracer = new line_tracer();
		while(true){
			if(tracer.isBlack()){
				wheel_actuator.forward(5);
			}
			else{
				wheel_actuator.backward(0.5);
				wheel_actuator.rotate(10);
				mia++;
			}
			if(mia >= 25 ){
				break;
			}
		}

	}

}
