package com.example.voter;


import  android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainCode extends Activity{
	LinearLayout layout;
	Button launch;
	Button join;
	Button help;
	Intent intent =new Intent();
	@Override
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (LinearLayout)findViewById(R.id.welcome);
		launch = (Button)findViewById(R.id.LaunchButton);
		join = (Button)findViewById(R.id.JoinButton);
		help = (Button)findViewById(R.id.HelpButton);
		
		launch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				intent.setClass(MainCode.this, Launch.class);
				startActivity(intent);
			}
		});
		
		join.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				intent.setClass(MainCode.this, Join.class);
				startActivity(intent);
			}
		});
	}
}