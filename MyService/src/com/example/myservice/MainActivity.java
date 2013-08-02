package com.example.myservice;

import com.example.myservice.MyService.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button buttonGo;
	Button buttonBind;
	Button buttonGet;
	boolean started;
	boolean mBound;
	MyService mService;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//        started = false;
//        mBound = false;
	}

	protected void onStart(){
		super.onStart();
		Log.v("mainActivity.onStart","Intent");        

		final Button buttonGo = (Button) findViewById(R.id.btnGo);
		buttonGo.setOnClickListener(this);
		final Button buttonBind = (Button) findViewById(R.id.btnBind);
		buttonBind.setOnClickListener(this);
		final Button buttonGet = (Button) findViewById(R.id.btnGet);
		buttonGet.setOnClickListener(this);
	}

	public void onClick(View src) {
		switch (src.getId()) {
		case R.id.btnGo:
			Log.d("test", "onClick: starting srvice");
			if (started) startService(new Intent(this, MyService.class));
			else stopService(new Intent(this, MyService.class));
			started=!started;
			break;
		case R.id.btnBind:
			Log.d("test", "onClick: starting srvice");
			if (started){
				Log.d("test", "Binding");
				Intent intent = new Intent(this, MyService.class);
				bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
				mBound=true;
				Log.d("test", "Should be binded");
			}else{
				unbindService(mConnection);
	            mBound = false;
			}
			break;
		case R.id.btnGet:
			if (mBound) {
				Log.d("test", "onClick: GET");
				int num = mService.getRandomNumber();
	            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
	            Log.d("test", "onClick: NUM" + num);
			}else{
				Log.d("test", "Binding");
				Intent intent = new Intent(this, MyService.class);
				bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
				mBound=true;
				Log.d("test", "Should be binded");
			}
			break;
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className,
				IBinder service) {
			// We've bound to LocalService, cast the IBinder and get LocalService instance
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
