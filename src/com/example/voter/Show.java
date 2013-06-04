package com.example.voter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.voter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
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
		
		button_create = (Button)findViewById(R.id.buttom_create);
		button_create.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					URL url = new URL(WebAccess.getCreateUrl(new_vote));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO post
			}
		});
	}
}