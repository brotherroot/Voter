package com.example.voter;

import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
	public static String getCreateUrl(VoteClass vote) {
		String urlPath = HostName + "/submit?topic=" + vote.getTopic() +
			"&launcher=" + vote.getLauncher() +
			"&description=" + vote.getDescription() +
			"&type=" + (vote.isSingle() ? "single" : "multi") +
			"&password=" + vote.getPassword() +
			"&option=[";
		ArrayList<OptionClass> options = vote.getOptions();
		for (OptionClass option: options) {
			urlPath.concat("'" + option.getOption() + "',");
		}
		urlPath.concat("]");
		return urlPath;
	}
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
		
		Log.d("WebAccess", "connect built");
		
		XmlPullParser parser = Xml.newPullParser();
		
		Log.d("WebAccess", "parser built");
		
		parser.setInput(connect.getInputStream(),"utf-8");
		
		Log.d("WebAccess", "parser set");
		
		return parser;
	}
	
//    /**
//     * 此方法设计用于从指定的Layout对象中读取子元素，即是用来填写各个选项的EdotText
//     * 或者是层嵌套的其他Layout，甄别所属、提取信息并提交到服务端
//     * 此方法的能否正确执行在极大程度上依赖于具体用于填写投票表单的布局文件设计
//     * 现在假定的布局是，题目存在于传入线性布局器的第一个元素
//     * 该线性布局的第二个元素是一个线性布局器
//     * 其中容纳所有的投票选项
//     * 因此在正是启用这个方法之前
//     * ！！！必须根据实际的布局文件进行进步一步的修改！！！
//     * ！！！为了应对异常情况，此方法抛出一个异常，必须捕获并处理它！！！
//     *
//     * @param rootLayout  传入这个方法的LinearLayout对象
//     * @throws IOException  方法执行发生错误时抛出的异常
//     */
//    static void submitVote(LinearLayout rootLayout){
//
//        View child = null;
//        LinearLayout linearLayout = null;
//        List<NameValuePair> dataList = new ArrayList<NameValuePair>();
//        String fromEditText = null;
//        int rootCount = rootLayout.getChildCount();
//        int childCount = 0;
//        // 从布局中提取子元素的方法基本就是这样
//        // 以下代码用于提取问题本身的题干
//        for (int i = 0; i < rootCount; i++)
//        {
//            child = rootLayout.getChildAt(i);
//            if (child.getClass() == EditText.class)
//            {
//                dataList.add(new BasicNameValuePair("title",((EditText)child).getText().toString()));
//                continue;
//            }
//            if(child.getClass() == LinearLayout.class)
//            {
//                // 获取其中承载各个选项的线性布局器 并退出循环
//                linearLayout = (LinearLayout)child;
//                childCount = linearLayout.getChildCount();
//                break;
//            }
//        }
//        // 在新循环里逐一取得options
//        for(int i = 0; i < childCount; i++ )
//        {
//            child = linearLayout.getChildAt(i);
//            if(child.getClass() == EditText.class)
//            {
//                fromEditText = ((EditText)child).getText().toString();
//                if(fromEditText.length()>0)
//                {
//                    dataList.add(new BasicNameValuePair("Option",fromEditText));
//                }
//            }
//        }
//        // 向服务器提出HTTP-POST请求以提交投票
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(HostName+"/submit");
//        try {
//            httppost.setEntity(new UrlEncodedFormEntity(dataList));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        HttpResponse response =null;
//        try {
//            response = httpclient.execute(httppost);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
