package com.example.voter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class Show extends Activity{
	private TextView text_topic;
	private TextView text_description;
	private TextView text_launcher;
	private TextView text_lifetime;
	private TableLayout layout_options;
	private Button button_create;
	private VoteClass new_vote;
	private Thread webPostThread = null;

	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show);
		
		Intent intent = getIntent();
		new_vote = (VoteClass)intent.getSerializableExtra("data");
		
		text_topic = (TextView)findViewById(R.id.topic);
		text_topic.setText(new_vote.getTopic().toString());
		
		text_description = (TextView)findViewById(R.id.description);
		text_description.setText(new_vote.getDescription().toString());
		
		text_launcher = (TextView)findViewById(R.id.launcher);
		text_launcher.setText(new_vote.getLauncher().toString());
		
		text_lifetime = (TextView)findViewById(R.id.lifetime);
		text_lifetime.setText(new_vote.getLifeTime().toString());
		
		layout_options = (TableLayout)findViewById(R.id.options);
		ArrayList<OptionClass> options = new_vote.getOptions();
		TextView new_option = null;
		for (OptionClass option:options) {
			new_option = new TextView(this);
			new_option.setText(option.getOption());
			layout_options.addView(new_option);
		}
		
		webPostThread = new Thread(new WebPostThread(new_vote, this));
		button_create =  (Button)findViewById(R.id.button_create);
		button_create.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				webPostThread.start();
			}
		});
	}
}