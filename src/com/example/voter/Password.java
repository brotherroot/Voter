package com.example.voter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Password extends Activity{
	//TableLayout passw=(TableLayout)getLayoutInflater().inflate(R. layout.password,null);
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);
		LayoutInflater layoutInflater = LayoutInflater.from(this); 
        View myLoginView = layoutInflater.inflate(R.layout.password, null); 
         
        Dialog alertDialog = new AlertDialog.Builder(this). 
                setTitle("密码框"). 
                setIcon(R.drawable.ic_launcher). 
                setView(myLoginView). 
                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    } 
                }). 
                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
 
                  //  @Override 
                    public void onClick(DialogInterface dialog, int which) { 
                        // TODO Auto-generated method stub  
                    } 
                }). 
                create(); 
        alertDialog.show(); 
		//bn=(Button)findViewById(R.id.correct);
		//bn.setOnClickListener(new OnClickListener() {			
			//public void onClick(View v) {
				//finish();
			//}
		//});
		}
	
}

