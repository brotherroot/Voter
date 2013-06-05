package com.example.voter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
		
		button_launch = (Button) findViewById(R.id.launch);
		button_launch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkFilling() == false) {
					Toast.makeText(v.getContext(), "请完成表单", Toast.LENGTH_LONG).show();
					return;
				}
				VoteClass new_vote = new VoteClass(whole);
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", new_vote);
				Intent intent = new Intent(Launch.this, Show.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	public boolean checkFilling() {
		View child = null;
		int child_count = whole.getChildCount();
		for (int ci = 0; ci < child_count; ++ci) {
			child = whole.getChildAt(ci);
			if (child.getClass() == EditText.class) {
				if (((EditText) child).getText().toString().length() == 0) {
					return false;
				}
			}
			if (child.getClass() == LinearLayout.class) {
				LinearLayout set = (LinearLayout) child;
				View cell = null;
				for (int i = 0; i < set.getChildCount(); ++i) {
					cell = set.getChildAt(i);
					if (cell.getClass() == EditText.class) {
						if (((EditText) cell).getText().toString().length() == 0) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}