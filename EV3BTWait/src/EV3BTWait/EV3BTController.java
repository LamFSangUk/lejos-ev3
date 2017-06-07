package EV3BTWait;

import java.io.DataInputStream;
import java.io.IOException;

//import lejos.hardware.Bluetooth;
import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.NXTConnection;
//import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
public class EV3BTController  {
	static BTConnector connector = new BTConnector();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Boolean isRunning = true;

		// Main loop   
		//while (true)
		//{
			
				LCD.drawString("ReadyforBT",0,1);
				LCD.refresh();

				// Listen for incoming connection

				NXTConnection btc = connector.waitForConnection(10000, NXTConnection.RAW);

				//btc.setIOMode(NXTConnection.RAW);

				LCD.clear();
				LCD.drawString("Connected",0,1);
				LCD.refresh();  
				
			

		  // The InputStream for read data 
		  DataInputStream dis = btc.openDataInputStream();


		  // Loop for read data                                                 
		  while(isRunning){
		   Byte n = dis.readByte();
		   
		   if((int)n==83){
			   LCD.clear();
				LCD.drawString("START",4,4);
				LCD.refresh();
		   }
		   else if((int)n==80){
			   LCD.clear();
				LCD.drawString("STOP",4,4);
				LCD.refresh();
		   }
		   else if((int)n==82){
			   LCD.clear();
				LCD.drawString("Restart",4,4);
				LCD.refresh();
		   }
		  }
		   
		   
		   
		   //LCD.clear();
		   //LCD.drawInt(n, 4, 4);

		  dis.close();

		  // Wait for data to drain
		  Thread.sleep(100); 

		  LCD.clear();
		  LCD.drawString("Closing",0,1);
		  LCD.refresh();

		  btc.close();

		  LCD.clear();
		//}
	}

}
