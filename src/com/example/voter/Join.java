package com.example.voter;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Join extends Activity {
	EditText inputvoteidEditText;
	Button confirmButton;
	
	@Override
	public void onCreate(Bundle saveInstanceState) {
		Bundle savedInstanceState = null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);
		inputvoteidEditText = (EditText)findViewById(R.id.get_voteid);
		confirmButton = (Button)findViewById(R.id.button_ok);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int voteid = Integer.parseInt(inputvoteidEditText.getText().toString());

				URL url = null;
				try {
					url = new URL("http://10.0.2.2:8080/request?id=" + voteid);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				HttpURLConnection dbConnection = null;
				try {
					dbConnection = (HttpURLConnection)url.openConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dbConnection.setDoOutput(true);
				
				XmlPullParser parser = Xml.newPullParser();
				try {
					parser.setInput(dbConnection.getInputStream(),"utf-8");
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				VoteClass vote_status = null;
				try {
					vote_status = new VoteClass(parser);
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bundle data =new Bundle();
				data.putSerializable("vote_status", vote_status);
				Intent intent = new Intent(Join.this, Vote.class);
				intent.putExtra("VoteClass", vote_status);
				startActivity(intent);
			}
		});
	}
}
