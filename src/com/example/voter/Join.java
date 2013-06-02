package com.example.voter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
				/*
				 * ��������ַ
				 */
				URL url = null;
				try {
					url = new URL("http://10.0.2.2:8080/request?id=" + voteid);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					Log.e("Join", "URL Visit Error");
					e.printStackTrace();
					return;
				}
				/*
				 * ��HttpURLConnection����
				 */
				HttpURLConnection dbConnection = null;
				try {
					dbConnection = (HttpURLConnection)url.openConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e("Join", "HTTP Connect IO Error");
					e.printStackTrace();
					return;
				}
				dbConnection.setDoOutput(true);
				/*
				 * ����XmlPullParser����
				 */
				XmlPullParser parser = Xml.newPullParser();
				try {
					parser.setInput(dbConnection.getInputStream(),"utf-8");
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e("Join", "XML parser IO Error");
					new AlertDialog.Builder(Join.this)
						.setTitle("IO����")
						.setMessage("�޷���ȡ����")
						.setPositiveButton("ȷ��", null)
						.show();
					e.printStackTrace();
					return;
				}
				/*
				 * ����VoteClass����
				 */
				VoteClass vote_status = null;
				try {
					vote_status = new VoteClass(parser);
				} catch (BadIdError e) {
					e.printStackTrace();
					new AlertDialog.Builder(Join.this)
						.setTitle("ͶƱID����")
						.setMessage("ID " + voteid + " �Ƿ�")
						.setPositiveButton("ȷ��", null)
						.show();
					return;
				}catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				/*
				 * ������һҳ��
				 */
				Bundle data =new Bundle();
				data.putSerializable("data", vote_status);
				Intent intent = new Intent(Join.this, Vote.class);
				intent.putExtras(data);
				startActivity(intent);
			}
		});
	}
}
