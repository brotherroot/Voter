package com.example.voter;

import android.app.Activity;
import android.os.Bundle;

public class Show extends Activity{
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showvote);
	}
	
}