package maze_escape;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.hardware.Bluetooth;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import maze_escape.save_path.Direction;

//Shared Data between two threads
class SharedArea{
	NXTConnection connection;
	DataInputStream dis;
	DataOutputStream dos;
	public boolean isRunning;
	public boolean isEscaping;
	
	public void send_res(Direction[] path){
		//DataOutputStream dos = btc.openDataOutputStream();
		
		try {
			LCD.drawInt(5,4,5);
			dos.writeInt(5);
			dos.flush();
			dos.writeInt(save_path.index);
			dos.flush();
			for(int i=0;i<save_path.index;i++){
				int send=path[i].getValue();
				dos.writeInt(send);
				dos.flush();
			}
			//dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

public class MainActivity{
	
	
	public static void main(String[] args) {
		BTConnector connector = new BTConnector();
		SharedArea sa = new SharedArea();
		
		EV3BTwaiting thread1 = new EV3BTwaiting();
		EV3mazeescpae thread2 = new EV3mazeescpae();
		
		sa.isRunning=true;
		sa.isEscaping=false;
		sa.connection=null;
		sa.dis=null;
		sa.dos=null;
		thread1.sa_write = sa;
		thread2.sa_read = sa;
		
		//Connect to android app with BT
		LCD.drawString("ReadyforBT", 0, 1);
		LCD.refresh();
		sa.connection = connector.waitForConnection(10000, NXTConnection.RAW);
		sa.dis=sa.connection.openDataInputStream();
		sa.dos=sa.connection.openDataOutputStream();
		LCD.clear();
		LCD.drawString("Connected", 0, 1);
		LCD.refresh();
		
		thread1.start();
		thread2.start();
		
	}
	
}

class EV3BTwaiting extends Thread{
	
	SharedArea sa_write;
	//static NXTConnection btc;
	
	
	public void run(){
		// TODO Auto-generated method stub
		// The InputStream for read data
		
		//DataInputStream dis = btc.openDataInputStream();

		Byte n;
		try {
			
			n = sa_write.dis.readByte();
			//System.out.println(dis.available());
			System.out.println(n);
			if (n==1) {
				LCD.clear();
				LCD.drawString("START", 4, 4);
				LCD.refresh();
				sa_write.isEscaping = true;
			
			}
			//dis.close();
			
			while (true) {

				n = sa_write.dis.readByte();
				if(n==-1) continue;
				System.out.println(n);
				
				if (n == 2) {
					LCD.clear();
					LCD.drawString("STOP", 4, 4);
					LCD.refresh();
					sa_write.isEscaping = false;
				} else if (n == 3) {
					LCD.clear();
					LCD.drawString("Restart", 4, 4);
					LCD.refresh();
					sa_write.isEscaping = true;
				} else if(n == 4){
					LCD.clear();
					LCD.drawString("END",4, 4);
					Sound.beep();
					Thread.sleep(5000);
					break;
				}
				
				//dis.close();

			}

			//dis.close();

			//Wait for data to drain
			//Thread.sleep(100);

			LCD.clear();
			LCD.drawString("Closing", 4, 4);
			LCD.refresh();

			//btc.close();

			//LCD.clear();
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

class EV3mazeescpae extends Thread{
	SharedArea sa_read;
	
	public void run() {
		
		float color;
		line_tracer tracer = new line_tracer();
		save_path.changeDirection(save_path.angle);
		while (sa_read.isRunning) {
			while (sa_read.isEscaping) {
				System.out.println("There");
				color = color_sensor.getColor();
				if(color!=-1){ // 벽이 있는 경우
				
					if(tracer.isOnline()){		
						
						if(color_sensor.isBlackBarrier(color)){
							wheel_actuator.forward(1);
							color = color_sensor.getColor();
							if(color_sensor.isBlackBarrier(color)){
								wheel_actuator.stop();
								LCD.clear();
								LCD.drawString("ESCAPE!", 4, 3);
								//System.out.println(save_path.index);
								
								sa_read.isRunning=false;
								sa_read.isEscaping=false;
								sa_read.send_res(save_path.path);
								break;
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
								LCD.clear();
								LCD.drawString("ESCAPE!", 4, 3);
								
								sa_read.isRunning=false;
								sa_read.isEscaping=false;
								sa_read.send_res(save_path.path);
								break;
							}
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