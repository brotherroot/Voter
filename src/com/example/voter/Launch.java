package com.example.voter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Launch extends Activity{
	private static final android.widget.RadioGroup.OnCheckedChangeListener listen = null;
	int opnum=2;
	Button singleoption;
	Button multioption;
	LinearLayout formsetLayout;
	Button add_optionButton, savevoteButton;
	Button additionButton;
	Intent intent1 =new Intent();
    EditText new_optionEditText;
	EditText formeroptionEditText;
	EditText passwordmarkEditText;
	RadioButton passwordButton;
	RadioButton nonpasswordButton;
	RadioGroup radioGroup;
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launch);
		singleoption=(Button)findViewById(R.id.sing);
		multioption=(Button)findViewById(R.id.multi);
		radioGroup=(RadioGroup)findViewById(R.id.sel2);
		radioGroup.setOnCheckedChangeListener(listen);
		
		nonpasswordButton=(RadioButton)findViewById(R.id.self);
		passwordButton=(RadioButton)findViewById(R.id.pub);
		passwordButton.setOnClickListener(new View.OnClickListener() {       
		       public void onClick(View v) {
	    	   intent1.setClass(Launch.this,Password.class);
				startActivity(intent1);}});
		//OnCheckedChangeListener  listen=new OnCheckedChangeListener() {
	         //@Override
			//public void onCheckedChanged(CompoundButton buttonView,
				//	boolean isChecked) {
	        	 //switch (radioGroup.getCheckedRadioButtonId()) {
	        	   //          case R.id.pub:
	        	     //            break;
	        	       //      case R.id.self:
	        	         //   	 {
	        	            		 
	        	    		//				}
	        	    			//    });
	        	                 //break;
	        	             //}				
			//}
	         //}
		 //};
		
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

		 }
	
