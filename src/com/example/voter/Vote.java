package com.example.voter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Vote extends Activity {
	LinearLayout layout;
	TextView text_id;
	TextView text_launcher;
	TextView text_topic;
	TextView text_description;
	RadioGroup group_options;
	Button button_vote;
	
	@SuppressLint("NewApi")
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
		
		group_options = (RadioGroup)findViewById(R.id.options);
		ArrayList<OptionClass> options = vote_status.getOptions();
		RadioButton new_button = null;
		TextView new_weight = null;
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		param.rightMargin = -10; 
		for (OptionClass option:options) {	
			new_button = new RadioButton(this);
			new_button.setText(option.getOption());
			group_options.addView(new_button);
			
			new_weight = new TextView(this);
			new_weight.setText(option.getWeight().toString());
			new_weight.setLayoutParams(param);
			
			group_options.addView(new_weight);
		}
		group_options.check(1);
		
		
		button_vote = (Button)findViewById(R.id.buttom_vote);
		button_vote.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d("Vote", "id =  " + group_options.getCheckedRadioButtonId());
				String select = "[" + (group_options.getCheckedRadioButtonId() -1) + "]";
				// TODO post
			}
		});
	}
}
