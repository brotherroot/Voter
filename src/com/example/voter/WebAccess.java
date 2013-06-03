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
		
		Log.d("WebAccess", "connect built");
		
		XmlPullParser parser = Xml.newPullParser();
		
		Log.d("WebAccess", "parser built");
		
		parser.setInput(connect.getInputStream(),"utf-8");
		
		Log.d("WebAccess", "parser set");
		
		return parser;
	}
	
//    /**
//     * �˷���������ڴ�ָ����Layout�����ж�ȡ��Ԫ�أ�����������д����ѡ���EdotText
//     * �����ǲ�Ƕ�׵�����Layout�������������ȡ��Ϣ���ύ�������
//     * �˷������ܷ���ȷִ���ڼ���̶��������ھ���������дͶƱ���Ĳ����ļ����
//     * ���ڼٶ��Ĳ����ǣ���Ŀ�����ڴ������Բ������ĵ�һ��Ԫ��
//     * �����Բ��ֵĵڶ���Ԫ����һ�����Բ�����
//     * �����������е�ͶƱѡ��
//     * ��������������������֮ǰ
//     * �������������ʵ�ʵĲ����ļ����н���һ�����޸ģ�����
//     * ������Ϊ��Ӧ���쳣������˷����׳�һ���쳣�����벶�񲢴�����������
//     *
//     * @param rootLayout  �������������LinearLayout����
//     * @throws IOException  ����ִ�з�������ʱ�׳����쳣
//     */
//    static void submitVote(LinearLayout rootLayout){
//
//        View child = null;
//        LinearLayout linearLayout = null;
//        List<NameValuePair> dataList = new ArrayList<NameValuePair>();
//        String fromEditText = null;
//        int rootCount = rootLayout.getChildCount();
//        int childCount = 0;
//        // �Ӳ�������ȡ��Ԫ�صķ���������������
//        // ���´���������ȡ���Ȿ������
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
//                // ��ȡ���г��ظ���ѡ������Բ����� ���˳�ѭ��
//                linearLayout = (LinearLayout)child;
//                childCount = linearLayout.getChildCount();
//                break;
//            }
//        }
//        // ����ѭ������һȡ��options
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
//        // ����������HTTP-POST�������ύͶƱ
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
