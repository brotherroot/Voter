package com.example.voter;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class WebGetThread implements Runnable {
	Context context;
	int voteid;
	public WebGetThread(int voteid, Context context) {
		this.voteid = voteid;
		this.context = context;
	}
	@Override
	public void run() {
		try {
			Log.d("Join", "before");
			Log.d("Join", "path " + WebAccess.getRequestUrl(voteid));

			XmlPullParser parser = WebAccess.getXML(WebAccess
					.getRequestUrl(voteid));
			VoteClass vote_status = new VoteClass(parser);
			Log.d("Join", "vote built");
			Bundle data = new Bundle();
			data.putSerializable("data", vote_status);
			Intent intent = new Intent(context, Vote.class);
			intent.putExtras(data);
			context.startActivity(intent);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("WebGet","IO����:�޷���ȡ����");
			Looper.prepare();
			Toast.makeText(context, "IO����:�޷���ȡ����", Toast.LENGTH_LONG)
					.show();
			Looper.loop();
			return;
		} catch (BadIdError e) {
			e.printStackTrace();
			Log.e("WebGet","������IDΪ" + voteid + "��ͶƱ");
			Looper.prepare();
			Toast.makeText(context, "������IDΪ" + voteid + "��ͶƱ",
					Toast.LENGTH_LONG).show();
			Looper.loop();
			return;
		}
	}
}