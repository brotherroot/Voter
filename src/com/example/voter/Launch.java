package com.example.voter;

import com.example.voter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Launch extends Activity {
	int opnum = 2;
	RelativeLayout whole;
	LinearLayout layout_options;
	Button button_add;
	Button button_launch;
	
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch);
		
		whole = (RelativeLayout)findViewById(R.id.whole);	
		
		layout_options = (LinearLayout)findViewById(R.id.option);
		button_add = (Button)findViewById(R.id.add_option);
		button_add.setOnClickListener(new View.OnClickListener() {
			@Override
			 public void onClick(View v) {
				 ++opnum;
				 EditText new_option = new EditText(Launch.this);
				 new_option.setHint("请输入第" + opnum + "个选项");
				 layout_options.addView(new_option);
			 }
		});
		
		button_launch = (Button)findViewById(R.id.launch);
		button_launch.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				VoteClass new_vote = new VoteClass(whole);
				
				Bundle bundle =new Bundle();
				bundle.putSerializable("data", new_vote);
				Intent intent = new Intent(Launch.this, Show.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
	
