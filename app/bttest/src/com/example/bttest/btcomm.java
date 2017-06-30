package com.example.bttest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class btcomm {

  //Target NXTs for communication
  final String ev3 = "00:16:53:47:E8:83";
  private BluetoothAdapter localAdapter;
  private static BluetoothSocket socket_ev3;
  InputStream is;
  OutputStream os;
  boolean success = false;

  // Enables Bluetooth if not enabled
  public void enableBT(){
    localAdapter = BluetoothAdapter.getDefaultAdapter();
    // If Bluetooth not enable then do it
    if (!localAdapter.isEnabled()) {
      localAdapter.enable();
      while(!(localAdapter.isEnabled()));
    }
  }

  // Connect to both NXTs
  public boolean connectToEV3() {

    // Get the BluetoothDevice of the NXT
    BluetoothDevice ev3_bt = localAdapter.getRemoteDevice(ev3);
    //BluetoothDevice nxt_1 = localAdapter.getRemoteDevice(nxt1);
    // Try to connect to the nxt
    try {
      socket_ev3 = ev3_bt.createRfcommSocketToServiceRecord(UUID
          .fromString("00001101-0000-1000-8000-00805F9B34FB"));

      socket_ev3.connect();   
      
      is=socket_ev3.getInputStream();
      os=socket_ev3.getOutputStream();

      new Thread(){
    	  public void run(){
    		  while(true){
    			  try{
    				  //Log.d("BT-is","READING THE DATA");
    				  
    				  int read_int=0;
    				  for(int i=0;i<4;i++){
    					  int temp=is.read();
    					  read_int=read_int|temp;
    					  if(i==3) break;
    					  read_int=read_int<<8;
    				  }
    				  
    				  if(read_int==5){
    					  Log.d("reading","HERE");
    					  //TODO: path출력
    					  int len=0;
    					  for(int i=0;i<4;i++){
    						  int temp=is.read();
        					  len=len|temp;
        					  if(i==3) break;
        					  len=len<<8;
    					  }
    					  Log.d("reading",Integer.toString(len));
    					  for(int i=0;i<len;i++){
    						  int getValue=0;
    						  for(int j=0;j<4;j++){
    							  getValue=is.read();
    						  }
    						  Log.d("reading",Integer.toString(getValue));
    						  MainActivity.saveData(getValue);
    					  }    						 
    					  
    					  Byte send_byte=4;
    					  os.write(send_byte);
    					  os.flush();
    					  break;
    				  }		   				  
    			  } catch (IOException e){
    				  //Log.e("BT-is","ERROR with READING");
    			  }
    		  }
    	  };
      }.start();
      
      success = true;

    } catch (IOException e) {
      Log.d("Bluetooth","Err: Device not found or cannot connect");
      success=false;
    }
    return success;    
  }
  public void sendByte(Byte send_byte){
	  try {
		os.write(send_byte);
		os.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}