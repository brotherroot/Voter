package com.example.voter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainCode extends Activity{
	Button launch;
	Button join;
	Button help;
	Intent intent =new Intent();
	@Override
	public void onCreate(Bundle saveInstanceState){
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		launch = (Button)findViewById(R.id.LaunchButton);
		join = (Button)findViewById(R.id.JoinButton);
		help = (Button)findViewById(R.id.AboutButton);
		
		launch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				intent.setClass(MainCode.this, Launch.class);
				startActivity(intent);
			}
		});
		
		join.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				intent.setClass(MainCode.this, Join.class);
				startActivity(intent);
			}
		});
		
		help.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog(v.getContext());
			}
		});
	}
	
	protected void dialog(final Context context) {
		  AlertDialog.Builder builder = new Builder(context);
		  builder.setMessage("不要点确定");
		  builder.setTitle("温馨提示");
		  builder.setPositiveButton("确定", new OnClickListener() {
		  public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		    String omg = "";
		    System.out.println(omg.length());
		   }
		  });
		  builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		  builder.create().show();
		 }
	
}