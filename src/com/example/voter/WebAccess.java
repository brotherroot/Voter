package com.example.voter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

/**
 * Created by Eizo on 13-5-29.
 * Edited by BrotherRoot on 13-6-3
 */
public class WebAccess {
	// ����android�������˵ 10.0.2.2���Ǳ�����localhost
	static final String HostName = "http://10.0.2.2:8080";
	
	/**
	 * ���ز�ѯͶƱ�ķ���������·��
	 * @param id ͶƱID
	 * @return
	 */
	public static String getRequestUrl(Integer id) {
		return HostName + "/request?id=" + id;
	}
	
	/**
	 * ���ش���ͶƱ�ķ���������·��
	 * @param  vote    ��ʾͶƱ��VoteClass����
	 * @return urlPath ����������·��
	 */
	/**
	 * �����ύͶƱ�ķ���������·��
	 * @param  id      ͶƱID
	 * @param  votes   ѡ���ѡ����
	 * @return urlPath ����������·��
	 */
	public static String getVoteUrl(Integer id, List<Integer> votes)
    {
		String urlPath = HostName + "/vote?id=" + id;
		for (Integer vote: votes) {
			urlPath.concat(vote.toString());
		}
		return urlPath;
	}
	/**
	 * �������ڶ�ȡ��������ͶƱ�����XmlPullParser����
	 * @param  urlPath ����������·��
	 * @return parser  ȡ�õ�XmlPullParser����
	 */
	public static XmlPullParser getXML(String urlPath)
			throws XmlPullParserException, IOException {
		URL url=new URL(urlPath);
		
		Log.d("WebAccess", "url built");
		
		HttpURLConnection connect = (HttpURLConnection)url.openConnection();
		connect.setDoInput(true);
		connect.connect();
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(connect.getInputStream(),"utf-8");
		return parser;
	}
}
