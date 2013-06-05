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
	// 对于android虚拟机来说 10.0.2.2就是本机的localhost
	static final String HostName = "http://10.0.2.2:8080";
	
	/**
	 * 返回查询投票的服务器访问路径
	 * @param id 投票ID
	 * @return
	 */
	public static String getRequestUrl(Integer id) {
		return HostName + "/request?id=" + id;
	}
	
	/**
	 * 返回创建投票的服务器访问路径
	 * @param  vote    表示投票的VoteClass对象
	 * @return urlPath 服务器访问路径
	 */
	/**
	 * 返回提交投票的服务器访问路径
	 * @param  id      投票ID
	 * @param  votes   选择的选项标号
	 * @return urlPath 服务器访问路径
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
	 * 返回用于读取服务器上投票对象的XmlPullParser对象
	 * @param  urlPath 服务器访问路径
	 * @return parser  取得的XmlPullParser对象
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
