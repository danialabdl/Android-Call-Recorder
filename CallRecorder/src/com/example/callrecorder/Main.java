package com.example.callrecorder;

import ir.adad.Adad;

import java.io.File;
import java.text.DecimalFormat;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Main extends Activity {
	static String theNumber;
	static String activateBtnText;
	static Boolean tgBool;
	File folder;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final EditText numberEditText = (EditText) findViewById(R.id.editText1);
	    final ToggleButton activateBtn = (ToggleButton) findViewById(R.id.toggleButton1);
		final RadioButton allRadio = (RadioButton) findViewById(R.id.radio0);
		final RadioButton oneRadio = (RadioButton) findViewById(R.id.radio1);
		Button sabtBtn = (Button) findViewById(R.id.button1);
		final Button fromContactsBtn = (Button) findViewById(R.id.button2);
		//Toast.makeText(this,"" + callReceiver.toggleBool ,Toast.LENGTH_SHORT).show();
		fromContactsBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				Intent pickIntent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
			pickIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
			startActivityForResult(pickIntent, 1);
			}
		});
		
		
		folder = new File(Environment.getExternalStorageDirectory() + "/Recorded Calls");
		if (!folder.exists()) {folder.mkdir();}
	/*	if (callReceiver.allowString == ("ON"))
		{ activateBtn.setChecked(true);
		}
		else if(callReceiver.allowString == ("OFF")) 
		{ activateBtn.setChecked(false);
		} 
		else {Toast.makeText(this,"i dont know",Toast.LENGTH_SHORT).show();
		Toast.makeText(this, callReceiver.allowString, Toast.LENGTH_SHORT).show();} */
		
		activateBtn.setChecked(callReceiver.toggleBool);
		//Toast.makeText(this, callReceiver.toggleBool.toString(), Toast.LENGTH_SHORT).show();
		allRadio.setChecked(callReceiver.allToggle);
		oneRadio.setChecked(callReceiver.oneToggle);
		numberEditText.setText(callReceiver.savedNumber);
		numberEditText.setEnabled(callReceiver.EditTextActivated);
		fromContactsBtn.setEnabled(callReceiver.EditTextActivated);
		numberEditText.setBackgroundResource(callReceiver.editTextColorInt);
		activateBtn.setBackgroundResource((Integer) callReceiver.back);
		
		
		
	/*		if (  callReceiver.userNumber ==("empty") || callReceiver.userNumber==null)
		{
			//allRadio.setChecked(true);
			numberEditText.setText(null);
			
		}
		else 
		{
			//oneRadio.setChecked(true);
			numberEditText.setText(callReceiver.userNumber);
			
		}
			*/
		
		
		
		
		
		
		
		numberEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (v.hasFocus() == false)
				{
					theNumber =   numberEditText.getText().toString();
				}
			}
		});
		//numberEditText.setEnabled(false);
	OnCheckedChangeListener checklistener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			 int a = buttonView.getId();
			switch (a) {
			case R.id.radio1:
				if (buttonView.isChecked())
				{
					
					numberEditText.setEnabled(true);
					fromContactsBtn.setEnabled(true);
					numberEditText.setBackgroundResource(color.background_light);
					
				}
				else {
					
					numberEditText.setEnabled(false);
					fromContactsBtn.setEnabled(false);
					numberEditText.setBackgroundResource(color.darker_gray);
					
					}
				
				break;
			
				
				

			default:
				break;
			}
			
		}
	};
	oneRadio.setOnCheckedChangeListener(checklistener);
	sabtBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myIntent = new Intent("123");
			/*if (activateBtn.isChecked()) 
			{myIntent.putExtra("456",true);  activateBtn.setBackgroundResource(R.drawable.on);
			myIntent.putExtra("back",R.drawable.on);
			} 
			else 
			{myIntent.putExtra("456",false);activateBtn.setBackgroundResource(R.drawable.off);
			myIntent.putExtra("back",R.drawable.off);
			}   */
			if (allRadio.isChecked())
			{
				theNumber = "empty";
				myIntent.putExtra("all",true);
				myIntent.putExtra("one",false);
				myIntent.putExtra("saved","");
				myIntent.putExtra("editTextActivated",false);
				myIntent.putExtra("editTextColor",color.darker_gray);
			}
			else if (oneRadio.isChecked()) {
				theNumber =   numberEditText.getText().toString();
				myIntent.putExtra("all",false);
				myIntent.putExtra("one",true);
				myIntent.putExtra("saved",numberEditText.getText().toString());
				myIntent.putExtra("editTextActivated", true);
				myIntent.putExtra("editTextColor",color.background_light);
				
			}
			
			
			myIntent.putExtra("number",theNumber);
			sendBroadcast(myIntent);
		}
	}); 
	activateBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			
		/*	if (isChecked)
			{activateBtnText = "ON";}
			else 
			{ activateBtnText = "OFF"; } */
			//activateBtnText = activateBtn.getText().toString();
			
			Intent allowIntent = new Intent("allow");
			if (isChecked) 
			{allowIntent.putExtra("456",true);  buttonView.setBackgroundResource(R.drawable.on);
			allowIntent.putExtra("back",R.drawable.on);
			} 
			else 
			{allowIntent.putExtra("456",false);buttonView.setBackgroundResource(R.drawable.off);
			allowIntent.putExtra("back",R.drawable.off);
			}
			//allowIntent.putExtra("456",activateBtnText);
			
			
			sendBroadcast(allowIntent);
		}
	});
	
	//startService(new Intent(getBaseContext(),recordService.class));
		/*	IntentFilter recIntent = new IntentFilter("android.intent.action.PHONE_STATE");
		
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
						Toast.makeText(context, "recording is done succesfully",Toast.LENGTH_LONG).show();
					
						
						
						}  
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			};
		};
		
		registerReceiver(recBroadcast, recIntent);  */
	
	
	}
	
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, 0, 0, "درباره");
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		EditText myEditText = (EditText) findViewById(R.id.editText1);
		
		super.onActivityResult(requestCode, resultCode, data);
	if (resultCode == RESULT_OK)
	{
		
		String phoneNum = null;
		String phoneNo = null;
		Uri uri = data.getData();
		Cursor cr = getContentResolver().query(uri, null, null, null, null);
		cr.moveToFirst();
		
		int phoneIndex = cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
		phoneNo =cr.getString(phoneIndex);
		if (phoneNo.startsWith("+98"))
		{
			phoneNum = phoneNo.replace("+98","0");
			myEditText.setText(phoneNum);
		}
		else 
		{
			myEditText.setText(phoneNo);
		}
		
	}
		
		
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId()==0)
		{
			Intent menuIntent = new Intent(Main.this,menuActivity.class);
		startActivity(menuIntent);
		}
		return super.onMenuItemSelected(featureId, item);
		
	}
	


}
