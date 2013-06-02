package com.example.voter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;


public class Vote extends Activity {
	LinearLayout layout;
	
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vote);
	}
}
