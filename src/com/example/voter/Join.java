package com.example.voter;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Join extends Activity {
	EditText inputvoteidEditText;
	Button confirmButton;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectDiskReads()
			.detectDiskWrites()
			.detectNetwork()
			.penaltyLog()
			.build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        	.detectLeakedSqlLiteObjects()
        	.detectLeakedClosableObjects()
        	.penaltyLog()
        	.penaltyDeath()
        	.build());
		
		
		inputvoteidEditText = (EditText)findViewById(R.id.get_voteid);
		confirmButton = (Button)findViewById(R.id.button_ok);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int voteid = Integer.parseInt(inputvoteidEditText.getText().toString());
				/*
				 * 服务器地址
				 */
				try {
					Log.d("Join", "before");
					Log.d("Join", "path " + WebAccess.getRequestUrl(voteid));
	
					XmlPullParser parser = WebAccess.getXML(WebAccess.getRequestUrl(voteid));
					
					VoteClass vote_status = new VoteClass(parser);
					
					Log.d("Join", "vote built");
					
					Bundle data =new Bundle();
					data.putSerializable("data", vote_status);
					Intent intent = new Intent(Join.this, Vote.class);
					intent.putExtras(data);
					startActivity(intent);
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(Join.this,"IO错误:无法读取数据", Toast.LENGTH_LONG).show();
					return;
				} catch (BadIdError e) {
					e.printStackTrace();
					Toast.makeText(Join.this,"不存在ID为" + voteid + "的投票", Toast.LENGTH_LONG).show();
					return;
				}
			}
		});
	}
}
