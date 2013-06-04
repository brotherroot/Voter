package com.example.voter;

import com.example.voter.Launch;
import com.example.voter.R;
import com.example.voter.Show;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class Launch extends Activity{
//	private static final android.widget.RadioGroup.OnCheckedChangeListener listen = null;
	int opnum=2;
	string password;
	RelativeLayout whole;
	Button singleoption;
	Button multioption;
	LinearLayout formsetLayout;
	Button add_optionButton, savevoteButton;
	Button additionButton;
	Button launchButton;
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
		
		whole = (RelativeLayout)findViewById(R.id.whole);	
		//passwordButton=(RadioButton)findViewById(R.id.pub);
//		passwordButton.setOnClickListener(new View.OnClickListener() {       
//		       public void onClick(View v) {
//	    	   add_password(); 
//	    	   }});
		formeroptionEditText=(EditText)findViewById(R.id.option);
		formsetLayout=(LinearLayout)findViewById(R.id.newvotes_layout);
		additionButton=(Button)findViewById(R.id.add_option);
		additionButton.setOnClickListener(new View.OnClickListener() {
			 public void onClick(View v) {
				 add_option(opnum);
				 opnum++;
			 }
		});
		launchButton=(Button)findViewById(R.id.launch);
		launchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VoteClass new_vote = new VoteClass(whole);
		
				Bundle bundle =new Bundle();
				bundle.putSerializable("data", new_vote);
				Intent intent = new Intent(Launch.this, Show.class);
				intent.putExtras(bundle);
				startActivity(intent);
				
//				Intent intent = new Intent(Launch.this, Show.class);
//				/* 通过Bundle对象存储需要传递的数据 */
//				Bundle bundle = new Bundle();
//				/* 字符、字符串、布尔、字节数组、浮点数等等，都可以传 */
//				bundle.putString("title", "12345");
//				bundle.putString("description", new_vote.getDescription());
//				bundle.putString("addid",new_vote.getLauncher());
//				/* 把bundle对象assign给Intent */
//				intent.putExtras(bundle);
//				startActivityForResult(intent, 0);
			}
		});
	}


	public  void add_option(int num){
		new_optionEditText=(EditText) new EditText(this);
		new_optionEditText.setHint("请输入第" + num + "个选项");
		formsetLayout.addView(new_optionEditText);
		
	}
	//public void add_password(){
	//	LayoutInflater layoutInflater = LayoutInflater.from(this); 
       // View myLoginView = layoutInflater.inflate(R.layout.password, null); 
         
       // Dialog alertDialog = new AlertDialog.Builder(this). 
        //        setTitle("密码框"). 
         //     setIcon(R.drawable.ic_launcher). 
           //     setView(myLoginView). 
            //    setPositiveButton("确定", new DialogInterface.OnClickListener() { 
 
              //      @Override 
               //     public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
             //   } 
             //  }). 
              //  setNegativeButton("取消", new DialogInterface.OnClickListener() { 
 
              //    @Override 
               //   public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
              //   } 
              //  }). 
           //     create(); 
      //  alertDialog.show(); 
	//}
		 }
	
