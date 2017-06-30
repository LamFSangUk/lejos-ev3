package com.example.bttest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private btcomm mBT = new btcomm();
	private static int path[] =new int[5000];
	private static int index;
	static View pathView;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		pathView=findViewById(R.id.canvas);
        index=0;
        
        findViewById(R.id.button_bt).setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        mBT.enableBT();
                        if(mBT.connectToEV3()){
                        	
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
		findViewById(R.id.path).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int startX=1250,startY=1250,endX=1250,endY=1250;
				int count=0;
				final int oneblockX=11,oneblockY=8;
				
		    	ImageView mImageView=(ImageView)pathView;
		        Bitmap bitmap = Bitmap.createBitmap(1300,1300,Bitmap.Config.ARGB_8888);
		        
		        Canvas canvas = new Canvas(bitmap);
		        
		        Paint paint = new Paint();
		        paint.setColor(Color.RED);
		        paint.setStyle(Paint.Style.STROKE);
		        paint.setStrokeWidth(10);
		        paint.setAntiAlias(true);
		        
		        while(count<index){
		        	if(path[count]==0){
		        		endY=startY-oneblockY;
		        	}
		        	else if(path[count]==1){
		        		endX=startX-oneblockX;
		        	}
		        	else if(path[count]==2){
		        		endY=startY+oneblockY;
		        	}
		        	else{
		        		endX=startX+oneblockX;
		        	}
		        	canvas.drawLine(startX, startY, endX, endY, paint);
		        	startX=endX;
		        	startY=endY;
		        	count++;
		        }
		        
		        paint.reset();
		        paint.setColor(Color.DKGRAY);
		        paint.setStyle(Paint.Style.STROKE);
		        paint.setStrokeWidth(10);
		        paint.setAntiAlias(true);

		        canvas.drawCircle(1250, 1250, 10, paint);
		        canvas.drawCircle(endX, endY, 10, paint);
		        
		        paint.reset();
		        paint.setColor(Color.DKGRAY);
		        paint.setTextSize(50);
		        paint.setAntiAlias(true);
		        
		        canvas.drawText("Start", 1100, 1250, paint);
		        canvas.drawText("End", endX+150, endY, paint);
		        
		        mImageView.setImageBitmap(bitmap);
		        sendPostData();
			}
		});
		/*findViewById(R.id.http).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// new Control().execute("restart");

				sendPostData();
			}
		});*/

    }
    
    public static void saveData(int data){
    	//ToastData(data);
    	Log.v("RESULTEV3", Integer.toString(data));
    	path[index++]=data;
    }
    
    public void sendPostData(){//Send path
		new Thread() {
			public void run() {
				try {
					String data = Arrays.toString(path);
					String link = "http://10.1.9.5:5000/path";
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(link);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("test", data));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
    }

}

