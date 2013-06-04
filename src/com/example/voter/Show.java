package com.example.voter;

import com.example.voter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Show extends Activity{
	
	private TextView titleTextView;
	private TextView descriptionTextView;
	private TextView LuncherTextView;
	private TextView TimeTextView;

	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showvote);
		
		Intent intent = getIntent();

		VoteClass new_vote = (VoteClass)intent.getSerializableExtra("data");
		
//		Bundle bundle = this.getIntent().getExtras();
//		final String title= bundle.getString("title");
//		final String description = bundle.getString("description");
//		final String addid = bundle.getString("addid");
//		
		titleTextView = (TextView)findViewById(R.id.title);
		descriptionTextView = (TextView)findViewById(R.id.describe);
		LuncherTextView = (TextView)findViewById(R.id.launcher);
		TimeTextView=(TextView)findViewById(R.id.timerequest);
		titleTextView.setText("您的话题是："+new_vote.getTopic());
		descriptionTextView.setText("描述:\n  "+new_vote.getDescription());
		LuncherTextView.setText("发起者："+new_vote.getLauncher());
		TimeTextView.setText("时限："+new_vote.getLeftTime());
		

	}
	
}