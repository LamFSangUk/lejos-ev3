package maze_escape;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

//Shared Data between two threads
class SharedArea{
	public boolean isRunning;
	public boolean isEscaping;
}

public class MainActivity{
	static BTConnector connector = new BTConnector();
	
	public static void main(String[] args) {
		SharedArea sa = new SharedArea();
		
		EV3BTwaiting thread1 = new EV3BTwaiting();
		EV3mazeescpae thread2 = new EV3mazeescpae();
		
		sa.isRunning=true;
		sa.isEscaping=false;
		thread1.sa_write = sa;
		thread2.sa_read = sa;
		
		//Connect to android app with BT
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
				sa_write.isEscaping = true;
			}
			
			while (sa_write.isRunning) {
				System.out.println("here");
				if (dis.available() == 1) {
					System.out.println("Waiting");
					n = dis.readByte();

					if ((int) n == 80) {
						LCD.clear();
						LCD.drawString("STOP", 4, 4);
						LCD.refresh();
						sa_write.isEscaping = false;
					} else if ((int) n == 82) {
						LCD.clear();
						LCD.drawString("Restart", 4, 4);
						LCD.refresh();
						sa_write.isEscaping = true;
					}
				}
				else
					continue;
			}

			dis.close();

			//Wait for data to drain
			//Thread.sleep(100);

			LCD.clear();
			LCD.drawString("Closing", 4, 4);
			LCD.refresh();

			btc.close();

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
		
		float color;
		line_tracer tracer = new line_tracer();
		save_path.changeDirection(save_path.angle);
		while (sa_read.isRunning) {
			while (sa_read.isEscaping) {
				System.out.println("There");
				color = color_sensor.getColor();
				if(color!=-1){ // ���� �ִ� ���
				
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
								break;
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
								LCD.clear();
								LCD.drawString("ESCAPE!", 4, 3);
								
								sa_read.isRunning=false;
								sa_read.isEscaping=false;
								break;
							}
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
						
						wheel_actuator.rotate(5);	// angle ���� �����Ѵ�
						save_path.angle +=5;
						save_path.changeDirection(save_path.angle);
					}
				}

			}
		}
	}
}