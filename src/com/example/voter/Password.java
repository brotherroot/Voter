package com.example.voter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Password extends Activity{
	Button bn;
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);
		bn=(Button)findViewById(R.id.correct);
		bn.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				finish();
			}
		});
		}
	
}

