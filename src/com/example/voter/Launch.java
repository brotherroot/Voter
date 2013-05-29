package com.example.voter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class Launch extends Activity{
	int opnum=2;
	Button singleoption;
	Button multioption;
	LinearLayout formsetLayout;
	Button add_optionButton, savevoteButton;
	Button additionButton;
	static EditText new_optionEditText;
	EditText formeroptionEditText;
	EditText passwordmarkEditText;
	RadioButton passwordButton;
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch);
		singleoption=(Button)findViewById(R.id.sing);
		multioption=(Button)findViewById(R.id.multi);
		passwordButton=(RadioButton)findViewById(R.id.self);
		passwordButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				add_password();					
			}
		});
		formeroptionEditText=(EditText)findViewById(R.id.de_vote_option_1);
		formsetLayout=(LinearLayout)findViewById(R.id.newvotes_layout);
		additionButton=(Button)findViewById(R.id.add_option);
		additionButton.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 add_option(opnum);
				 opnum++;
			 }
		});
	}
	public  void add_option(int num){
		new_optionEditText=(EditText) new EditText(this);
		new_optionEditText.setHint("请输入第" + num + "个选项");
		formsetLayout.addView(new_optionEditText);
		
	}
	public  void add_password(){
		passwordmarkEditText=(EditText) new EditText(this);
		passwordmarkEditText.setHint("请输入密码：\n");
		
		//passwordmarkEditText.addView(new_optionEditText);
		
	}
}