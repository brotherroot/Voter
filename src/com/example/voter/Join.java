package com.example.voter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Join extends Activity {
	ProgressBar progress;
	EditText id;
	Button confirm;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.join);
		
		/**
		 * 虽然我不知道这段代码是干什么的
		 * 但是我知道这东西不能删....
		 */
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads()
		.detectDiskWrites()
		.detectNetwork()
		.penaltyLog()
		.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
    	.detectLeakedSqlLiteObjects()
    	.detectLeakedClosableObjects()
    	.penaltyLog()
    	.penaltyDeath()
    	.build());
    
        progress = (ProgressBar)findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);
        id = (EditText)findViewById(R.id.id);
		confirm = (Button)findViewById(R.id.button_confirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(id.getText().toString().length()==0) {
					return;
				}
				int voteid = Integer.parseInt(id.getText().toString());
				Thread webGetThread = new Thread(new WebGetThread(voteid, Join.this));
				webGetThread.start();
			}
		});
	}
}
