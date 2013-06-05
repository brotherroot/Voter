package com.example.voter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

public class WebPostThread implements Runnable {
	HttpClient httpclient = null;
	HttpPost httpPost = null;
	HttpResponse response = null;
	Context context;
	VoteClass voteinfo = null;
	ArrayList<OptionClass> list = null;
	public WebPostThread(VoteClass voteinfo, Context context) {
		this.voteinfo = voteinfo;
		this.context = context;
		this.list = voteinfo.getOptions();
	}
	@Override
	public void run() {
		httpclient = new DefaultHttpClient();
		httpPost = new HttpPost(WebAccess.HostName + "/launch");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("topic",voteinfo.getTopic()));
		params.add(new BasicNameValuePair("launcher",voteinfo.getLauncher()));
		params.add(new BasicNameValuePair("description",voteinfo.getDescription()));
		params.add(new BasicNameValuePair("left_time",voteinfo.getLifeTime().toString()));
		for(int i = 0; i < list.size(); i++ )
		{
			params.add(new BasicNameValuePair("option",list.get(i).getOption()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String QID_BAKC = "null";
		try {
			QID_BAKC = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("RES", QID_BAKC);
		Looper.prepare();
		dialog(QID_BAKC);
		Looper.loop();
	}
	
	protected void dialog(String QID) {
		  AlertDialog.Builder builder = new Builder(context);
		  builder.setMessage("您的问题ID为"+QID+"\n请牢记该ID，在现有开发版本中，问题ID这是唯一能够用于标记问题的方式");
		  builder.setTitle("提交成功！");
		  builder.setPositiveButton("OK", new OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		    Intent i = new Intent(context, MainCode.class);
		    context.startActivity(i);
		   }
		  });
		  builder.create().show();
		 }
}