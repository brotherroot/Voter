package com.example.voter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

public class Vote extends Activity {
	TextView text_id;
	TextView text_launcher;
	TextView text_topic;
	TextView text_description;
	RadioGroup group_options;
	Button button_vote;
	TableLayout whole;

	@SuppressLint("NewApi")
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vote);

		Intent intent = getIntent();
		final VoteClass vote_status = (VoteClass) intent
				.getSerializableExtra("data");

		text_id = (TextView) findViewById(R.id.id);
		text_id.setText(vote_status.getId().toString());

		text_launcher = (TextView) findViewById(R.id.launcher);
		text_launcher.setText(vote_status.getLauncher().toString());

		text_topic = (TextView) findViewById(R.id.topic);
		text_topic.setText(vote_status.getTopic().toString());

		text_description = (TextView) findViewById(R.id.description);
		text_description.setText(vote_status.getDescription().toString());

		group_options = (RadioGroup) findViewById(R.id.options);

		whole = (TableLayout) findViewById(R.id.whole);

		ArrayList<OptionClass> options = vote_status.getOptions();
		RadioButton new_button = null;
		for (OptionClass option : options) {
			new_button = new RadioButton(this);
			new_button.setText(option.getOption());
			group_options.addView(new_button);
		}
		button_vote = (Button) findViewById(R.id.buttom_vote);
		button_vote.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 创建提交线程
				int ops = 0, delta = 0;
//				int ops = group_options.getCheckedRadioButtonId() - 1;
				View tmp = null;
				for(int i = 0; i < group_options.getChildCount(); i++ )
				{

					tmp = group_options.getChildAt(i);
					if(tmp.getClass() !=  RadioButton.class)
					{
						delta += 1;
						continue;
					}
					if(((RadioButton)tmp).isChecked() == true)
					{
						ops = i-delta;
						break;
					}
						
				}
				Log.e("Chicked-ID", "" + ops);
				if (ops >= 0 && ops < group_options.getChildCount()) {
					Thread webVoteThread = new Thread(new WebVoteThread(
							vote_status.getId(), new int[] { ops }, Vote.this));
					webVoteThread.start();
				} else {
					return;
				}
				// 启动绘图界面
				Bundle bundle = new Bundle();
				bundle.putSerializable("ID", vote_status.getId());
				Intent cintent = new Intent(Vote.this, CheckOut.class);
				cintent.putExtras(bundle);
				Vote.this.startActivity(cintent);
			}
		});
	}
}
