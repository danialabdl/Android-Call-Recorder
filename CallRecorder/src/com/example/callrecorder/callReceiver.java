package com.example.callrecorder;


import android.R.color;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.nsd.NsdManager.RegistrationListener;
import android.os.Environment;
import android.sax.StartElementListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class callReceiver extends BroadcastReceiver {
	String action = "allow" ;
	static String userNumber = "firstTime";
	static String allowString;
	String state;
	static String phoneNumber;
	static String toggleString ;
	static Boolean isMatch = false;
	static Boolean isRun  =false;
	static Integer toggleInt;
	static Boolean toggleBool =true ;
	static Boolean allToggle = true;
	static Boolean oneToggle = false;
	static String savedNumber = null;
	static Boolean EditTextActivated = false;
	static Integer editTextColorInt = color.darker_gray ;
	static Object back = R.drawable.on ;
	static Boolean forSure = false; 
	
	
	
//MediaRecorder myRec = new MediaRecorder();
//String outputFile;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		action = intent.getAction();
		//allowString= intent.getStringExtra("456");
		state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		//Toast.makeText(context,allowString  ,Toast.LENGTH_LONG).show();
		//Toast.makeText(context,"out space brfore allow :"+ toggleBool,Toast.LENGTH_SHORT).show();
		
		
		
	
		//	Toast.makeText(context,"out space after allow in else  :"+ toggleBool,Toast.LENGTH_SHORT).show();
		
		
		
		if (action.equals("123") || action.equals("allow"))
	
		{		if (action.equals("123"))
			{
				//toggleBool =  intent.getBooleanExtra("456",false);
				//Toast.makeText(context,"in 123 space:"+ toggleBool,Toast.LENGTH_SHORT).show();
				savedNumber = intent.getStringExtra("saved");// این متغیر صرفا برای استفاده اکتیویتی در هنگام لود شدن است
				editTextColorInt = intent.getIntExtra("editTextColor",color.darker_gray);
				EditTextActivated = intent.getBooleanExtra("editTextActivated",false);
				allToggle = intent.getBooleanExtra("all",true);
				oneToggle = intent.getBooleanExtra("one",false);
				userNumber = intent.getStringExtra("number");
				Toast.makeText(context,"تنظیمات ثبت شد",Toast.LENGTH_SHORT).show();
				
				
		    }  
		  
		
		   if (action.equals("allow"))
			{
		    	back = intent.getSerializableExtra("back");
		    	toggleBool =  intent.getBooleanExtra("456",false);
		    	//Toast.makeText(context,"in allow space:"+ toggleBool,Toast.LENGTH_SHORT).show();
		    	if (toggleBool)
		    	{Toast.makeText(context, "فعال",Toast.LENGTH_SHORT).show();}
		    	else { Toast.makeText(context, "غیرفعال",Toast.LENGTH_SHORT).show();}
				//toggleString = allowString;
				//Toast.makeText(context,allowString  ,Toast.LENGTH_LONG).show();
				
			}  
		
		    
			
		}	
		   
		    else if (!action.equals("123") && !action.equals("allow")) {
		    	
		    	//Toast.makeText(context, action,Toast.LENGTH_SHORT).show();
	
		 if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			 
			 if (userNumber.equals(phoneNumber)) {isMatch=true;} else {isMatch=false;}
			  forSure = true;
			//Toast.makeText(context, "phone is ringing-number: "  + phoneNumber, Toast.LENGTH_SHORT).show();
			//Toast.makeText(context, "the match statemaent is  "  +isMatch, Toast.LENGTH_SHORT).show();
			}  
		
		
		
		 if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) && forSure == true)
		{   
			// Toast.makeText(context,"out space after allow in offhook :"+ toggleBool,Toast.LENGTH_SHORT).show();
			if (toggleBool.equals(true))
			{
				if (userNumber.equals("empty") || userNumber.equals("firstTime")) 
				{ 
					
				Toast.makeText(context, "ضبط مکالمه آغاز شد" , Toast.LENGTH_LONG).show();	
					try {
						context.startService(new Intent(context, recordService.class));
						forSure = false;
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(context, "برنامه با مشکل مواجه شد" , Toast.LENGTH_SHORT).show();
					}
					
					isRun = true;  
				} 
			
				else {
						if (isMatch) 
						{
							Toast.makeText(context, "ضبط مکالمه یک شماره خاص آغاز شد" , Toast.LENGTH_LONG).show();	
							context.startService(new Intent(context, recordService.class));
							isRun = true;
						}
						else
						{
							isRun = false;
							//Toast.makeText(context, "service is not started,cause its not match"  , Toast.LENGTH_LONG).show();
						}
					} }
			//	Toast.makeText(context, "call received from: " + Main.theNumber , Toast.LENGTH_LONG).show();	
           // context.startService(new Intent(context, recordService.class));
           // isRun = true;

			else if (toggleBool.equals(false))
			{
				isRun = false;
				//Toast.makeText(context, "service is not started,cause its off"  , Toast.LENGTH_LONG).show();
			}
			
			else
			{
				isRun = false;
				Toast.makeText(context, "ضبط مکالمه انجام نشد"  , Toast.LENGTH_LONG).show();
			}
			
		}
		
		 if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) 
		{
			if (isRun)
			{
				//Toast.makeText(context, "phone is idle", Toast.LENGTH_LONG).show();	
				//Toast.makeText(context, "ضبط مکالمه با موفقیت انجام شد",Toast.LENGTH_LONG).show();
				try {
					context.stopService(new Intent(context, recordService.class));
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(context, "با مشکل مواجه شد", Toast.LENGTH_LONG).show();
				}
				
			
			}
		} 
	
	
		
		    }
		    else {}
		
		
		
	  
		 
		
	   // Toast.makeText(context,"out space after allow :"+ toggleBool,Toast.LENGTH_SHORT).show();
} }

		
		
	/*	myRec.setAudioSource(MediaRecorder.AudioSource.MIC);
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
		}  */
		
	 


