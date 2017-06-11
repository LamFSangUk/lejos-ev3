package com.example.bttest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.UUID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import lejos.remote.ev3.RemoteRequestEV3;

public class MainActivity extends Activity {
    
    private btcomm mBT = new btcomm();
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button_bt).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        mBT.enableBT();
                        if(mBT.connectToEV3()){
                        	/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        	builder.setMessage("Success").setPositiveButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
									
								}
                        	});*/
                        	Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        	//AlertDialog dialog = builder.create();
                        	//dialog.show();
                        }
                    }
                }
		);
		findViewById(R.id.button_start).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// new Control().execute("start");

				Byte n = 1;
				mBT.sendByte(n);
			}
		});
		findViewById(R.id.button_stop).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// new Control().execute("stop");

				Byte n = 2;
				mBT.sendByte(n);
			}

		});
		findViewById(R.id.button_restart).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// new Control().execute("restart");

				Byte n = 3;
				mBT.sendByte(n);
			}
		});

    }
    
    public static void printData(int data){
    	//ToastData(data);
    	Log.v("RESULTEV3", Integer.toString(data));
    }
   // public void ToastData(int data){
    	//Toast.makeText(getApplicationContext(), Integer.toString(data), Toast.LENGTH_LONG).show();

    //}
    
}

