package com.example.callrecorder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.widget.Toast;

public class recordService extends Service {
	
 java.text.DateFormat dateTimeNameFormat;
	Date myDate;
	String dateNameString;
	MediaRecorder myRec = new MediaRecorder();
	String outputFile;
	
	/*IntentFilter recIntent = new IntentFilter("android.intent.action.PHONE_STATE");
	BroadcastReceiver recBroadcast = new BroadcastReceiver()
	{
		MediaRecorder myRec = new MediaRecorder();
		String outputFile;
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			myRec.setAudioSource(MediaRecorder.AudioSource.MIC);
			myRec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			myRec.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
			outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myRecording.3gp";
			myRec.setOutputFile(outputFile);
			try {
				
				String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
				String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				Toast.makeText(context, "phone is ringing-number: " + phoneNumber, Toast.LENGTH_LONG).show();	
				}
				
				if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
				{
					Toast.makeText(context, "call received!" , Toast.LENGTH_LONG).show();	
					myRec.prepare();
					myRec.start();
					
				}
				
				if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
					Toast.makeText(context, "phone is idle", Toast.LENGTH_LONG).show();	
					myRec.stop();
					myRec.release();
					myRec = null;
					Toast.makeText(context, "recording is done successfully",Toast.LENGTH_LONG).show();
				
					
					
					}  
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		};
	}; */

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//Toast.makeText(this,"recording service started",Toast.LENGTH_LONG).show();
		dateTimeNameFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		
		myDate = new Date();
		
		dateNameString = dateTimeNameFormat.format(myDate);
		
		//Toast.makeText(this,dateNameString,Toast.LENGTH_LONG).show();
		//myRec.setAudioSource(MediaRecorder.AudioSource.MIC);
		myRec.setAudioSource(MediaRecorder.AudioSource.MIC);
		
		myRec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		
		myRec.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
	
		outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recorded Calls" + "/" + dateNameString+".3gp";
		myRec.setOutputFile(outputFile);
		
		try {
			myRec.prepare();
			myRec.start(); 
			}
		 catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			// دارم سعی می کنم که همزمان از دو منبع صدا استقاده کنم. هر وقت اولی جواب نداد دومی استفاده شود
			// myRec.setAudioSource(MediaRecorder.AudioSource.MIC);
		
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this,"ضبط با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
		}
		
		
			return START_NOT_STICKY;  // ###
		
		
		
	//	registerReceiver(recBroadcast, recIntent);
		}	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		try {
			if (callReceiver.isRun)
			{myRec.stop();
			myRec.release();
			myRec = null;
			Toast.makeText(this,"ضبط مکالمه پایان یافت",Toast.LENGTH_LONG).show();
			}
			else {Thread.currentThread().interrupt();}
			super.onDestroy();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getBaseContext(), "برنامه با مشکل مواجه شد", Toast.LENGTH_LONG).show();
		}
		
		
		
		//Toast.makeText(this, "recording is done successfully",Toast.LENGTH_LONG).show();
	
	}


}
