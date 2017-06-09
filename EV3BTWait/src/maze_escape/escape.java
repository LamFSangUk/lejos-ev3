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
			if(color!=-1){ // ���� �ִ� ���
			
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
						color_sensor.getColorMove(color);			// �� ���򿡼� direction �ٲ��ְ� yellow�� path�� �� �ִ°ű���
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
							color_sensor.getColorMove(color);// �� ���򿡼� direction �ٲ��ְ� yellow�� path�� �� �ִ°ű���
					}
					else{
						color_sensor.getColorMove(color);// �� ���򿡼� direction �ٲ��ְ� yellow�� path�� �� �ִ°ű���
					}
				}
			}
			else if(color==-1) // ���� ���� ���
			{
				if(tracer.isOnline()){		
					wheel_actuator.forward(wheel_actuator.one_block);	// path �� direction�־���
					save_path.saveToPath(save_path.current_dir);
				}
				else{
					
					wheel_actuator.rotate(10);	// angle ���� �����Ѵ�
					save_path.angle +=10;
					save_path.changeDirection(save_path.angle);
				}
			}
			
		}

	}

}
