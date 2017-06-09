package maze_escape;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.utility.Delay;

class SharedArea{
	public boolean isRunning;
}

public class MainActivity{
	static BTConnector connector = new BTConnector();
	
	public static void main(String[] args) {
		SharedArea sa = new SharedArea();

		EV3BTwaiting thread1 = new EV3BTwaiting();
		EV3mazeescpae thread2 = new EV3mazeescpae();
		
		sa.isRunning=false;
		thread1.sa_write = sa;
		thread2.sa_read = sa;

		LCD.drawString("ReadyforBT", 0, 1);
		LCD.refresh();
		thread1.btc = connector.waitForConnection(10000, NXTConnection.RAW);
		LCD.clear();
		LCD.drawString("Connected", 0, 1);
		LCD.refresh();
		
		thread1.start();
		thread2.start();
		
	}
	
}

class EV3BTwaiting extends Thread{
	
	SharedArea sa_write;
	NXTConnection btc;
	
	
	public void run(){
		// TODO Auto-generated method stub
		// The InputStream for read data
		
		DataInputStream dis = btc.openDataInputStream();

		Byte n;
		try {
			n = dis.readByte();
			if ((int) n == 83) {
				LCD.clear();
				LCD.drawString("START", 4, 4);
				LCD.refresh();
				sa_write.isRunning = true;
			}
			
			while (true) {
				n = dis.readByte();

				if ((int) n == 80) {
					LCD.clear();
					LCD.drawString("STOP", 4, 4);
					LCD.refresh();
					sa_write.isRunning=false;
				} else if ((int) n == 82) {
					LCD.clear();
					LCD.drawString("Restart", 4, 4);
					LCD.refresh();
					sa_write.isRunning=true;
				}
			}

			//dis.close();

			// Wait for data to drain
			//Thread.sleep(100);

			//LCD.clear();
			//LCD.drawString("Closing", 0, 1);
			//LCD.refresh();

			//btc.close();

			//LCD.clear();
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

class EV3mazeescpae extends Thread{
	SharedArea sa_read;
	
	public void run() {
		// TODO Auto-generated method stub
		float color;
		line_tracer tracer = new line_tracer();
		save_path.changeDirection(save_path.angle);
		while (true) {
			while (sa_read.isRunning) {
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
								//for(int j=0;j<save_path.index;j++)
								//	for(int k=0;k<10;k++)
								//		System.out.print(save_path.path[j]+" ");
								//	Delay.msDelay(5000);
								//Delay.msDelay(60000);
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
								//for(int j=0;j<save_path.index;j++)
								//	for(int k=0;k<10;k++)
								//		System.out.print(save_path.path[j]+" ");
								//	Delay.msDelay(5000);
								//Delay.msDelay(60000);
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
						
						wheel_actuator.rotate(5);	// angle 값을 갱신한다
						save_path.angle +=5;
						save_path.changeDirection(save_path.angle);
					}
				}

			}
		}
	}
}