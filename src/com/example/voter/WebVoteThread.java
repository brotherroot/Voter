package com.example.voter;

import java.io.IOException;
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

import android.content.Context;
import android.os.Looper;
import android.util.Log;

public class WebVoteThread implements Runnable {
	Context context;
	HttpClient httpclient = null;
	HttpPost httpPost = null;
	HttpResponse response = null;
	Integer QID = 0;
	int[] options = null;
	ArrayList<OptionClass> list = null;

	public WebVoteThread(int QID, int[] options, Context context) {
		this.options = options;
		this.context = context;
		this.QID = QID;
	}

	@Override
	public void run() {
		Looper.prepare();
		httpclient = new DefaultHttpClient();
		httpPost = new HttpPost(WebAccess.HostName + "/vote");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", QID.toString()));
		for (int i = 0; i < options.length; i++) {
			params.add(new BasicNameValuePair("option", "" + options[i]));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		Log.e("RES-HEADER", response.getStatusLine().getStatusCode() + "");
	}
}