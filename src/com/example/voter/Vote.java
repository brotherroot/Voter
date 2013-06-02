package com.example.voter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Vote extends Activity {
	LinearLayout layout;
	TextView text_id;
	TextView text_launcher;
	TextView text_topic;
	TextView text_description;
	TextView text_type;
	LinearLayout layout_options;
	
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vote);
		
		Intent intent = getIntent();
		VoteClass vote_status = (VoteClass)intent.getSerializableExtra("data");
		
		text_id = (TextView)findViewById(R.id.id);
		text_id.setText(vote_status.getId().toString());
		
		text_launcher = (TextView)findViewById(R.id.launcher);
		text_launcher.setText(vote_status.getLauncher().toString());
		
		text_topic = (TextView)findViewById(R.id.topic);
		text_topic.setText(vote_status.getTopic().toString());
		
		text_description = (TextView)findViewById(R.id.description);
		text_description.setText(vote_status.getDescription().toString());
		
		text_type = (TextView)findViewById(R.id.type);
		text_type.setText(getResources().getString(vote_status.isSingle() ? R.string.single : R.string.multiply));
		
		layout_options = (LinearLayout)findViewById(R.id.options);
		ArrayList<OptionClass> options = vote_status.getOptions();
		TextView new_option = null;
		for (OptionClass option:options) {
			new_option = (TextView)new TextView(this);
			new_option.setText(option.option);
			layout_options.addView(new_option);
		}
	}
}
