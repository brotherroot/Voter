package com.example.voter;


import  android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainCode extends Activity{
	Button launch;
	Button join;
	Button help;
	TextView show;
	LinearLayout layout;
	Intent intent =new Intent();
	@Override
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		launch=(Button)findViewById(R.id.LaunchButton);
		join=(Button)findViewById(R.id.JoinButton);
		help=(Button)findViewById(R.id.HelpButton);
		show=new TextView(this);
		//launch.setOnClickListener((OnClickListener) this);
		layout =(LinearLayout)findViewById(R.id.welcome);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(show);		
		launch.setOnClickListener(new View.OnClickListener() {
			       public void onClick(View v) {
			    	   intent.setClass(MainCode.this,Launch.class);
						startActivity(intent);
						}
			    });
}
}