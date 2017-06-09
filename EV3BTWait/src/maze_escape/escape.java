package maze_escape;

import lejos.utility.Delay;

public class escape {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		float color;
		line_tracer tracer = new line_tracer();
		save_path.changeDirection(save_path.angle);
		while(true){
			color = color_sensor.getColor();
			if(color!=-1){ // 벽이 있는 경우
			
				if(tracer.isOnline()){		
					
					if(color_sensor.isBlackBarrier(color)){
						wheel_actuator.forward(1);
						color = color_sensor.getColor();
						if(color_sensor.isBlackBarrier(color)){
							wheel_actuator.stop();
							System.out.println("ESCAPE!");
							System.out.println(save_path.index);
							for(int j=0;j<save_path.index;j++)
								for(int k=0;k<10;k++)
									System.out.print(save_path.path[j]+" ");
								Delay.msDelay(5000);
							Delay.msDelay(60000);
							break;///////////////////////////////////////end
						}
						else
							color_sensor.getColorMove(color);
					}
					else{
						color_sensor.getColorMove(color);			// 각 색깔에서 direction 바꿔주고 yellow는 path에 값 넣는거까지
					}
				}
				else{
					if(color_sensor.isBlackBarrier(color)){
						wheel_actuator.forward(1);
						color = color_sensor.getColor();
						if(color_sensor.isBlackBarrier(color)){
							wheel_actuator.stop();
							System.out.println("ESCAPE!");
							for(int j=0;j<save_path.index;j++)
								for(int k=0;k<10;k++)
									System.out.print(save_path.path[j]+" ");
								Delay.msDelay(5000);
							Delay.msDelay(60000);
							break;
						}//////////////////////////////////////////////////////end
						else
							color_sensor.getColorMove(color);// 각 색깔에서 direction 바꿔주고 yellow는 path에 값 넣는거까지
					}
					else{
						color_sensor.getColorMove(color);// 각 색깔에서 direction 바꿔주고 yellow는 path에 값 넣는거까지
					}
				}
			}
			else if(color==-1) // 벽이 없는 경우
			{
				if(tracer.isOnline()){		
					wheel_actuator.forward(wheel_actuator.one_block);	// path 에 direction넣어줌
					save_path.saveToPath(save_path.current_dir);
				}
				else{
					
					wheel_actuator.rotate(10);	// angle 값을 갱신한다
					save_path.angle +=10;
					save_path.changeDirection(save_path.angle);
				}
			}
			
		}

	}

}
