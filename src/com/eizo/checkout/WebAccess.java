package com.eizo.checkout;

import android.util.Pair;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eizo on 13-5-29.
 */
public class WebAccess {

    // ����android�������˵ 10.0.2.2���Ǳ�����localhost
    static String HostName = "http://10.0.2.2:8080";

    /**
     * �˷��������ڷ�����ȷ�ķ���������·��
     * @param qid           ���������ID
     * @param returnResult  �Ƿ�ͬʱ�����������ǰͶƱ���
     * @return
     */

    private static String getVoteUrl(int qid,boolean returnResult)
    {
        if(returnResult)
        {
            return "/request?qid="+qid+"&result=1";
        }
        else
        {
            return "/request?qid="+qid+"&result=0";
        }
    }

    /**
     * �˷���������ڴ�ָ����Layout�����ж�ȡ��Ԫ�أ�����������д����ѡ���EdotText
     * �����ǲ�Ƕ�׵�����Layout�������������ȡ��Ϣ���ύ�������
     * �˷������ܷ���ȷִ���ڼ���̶��������ھ���������дͶƱ���Ĳ����ļ����
     * ���ڼٶ��Ĳ����ǣ���Ŀ�����ڴ������Բ������ĵ�һ��Ԫ��
     * �����Բ��ֵĵڶ���Ԫ����һ�����Բ�����
     * �����������е�ͶƱѡ��
     * ��������������������֮ǰ
     * �������������ʵ�ʵĲ����ļ����н���һ�����޸ģ�����
     * ������Ϊ��Ӧ���쳣������˷����׳�һ���쳣�����벶�񲢴�����������
     *
     * @param rootLayout  �������������LinearLayout����
     * @throws IOException  ����ִ�з�������ʱ�׳����쳣
     */
    static void submitVote(LinearLayout rootLayout){

        View child = null;
        LinearLayout linearLayout = null;
        List<NameValuePair> dataList = new ArrayList<NameValuePair>();
        String fromEditText = null;
        int rootCount = rootLayout.getChildCount();
        int childCount = 0;
        // �Ӳ�������ȡ��Ԫ�صķ���������������
        // ���´���������ȡ���Ȿ������
        for (int i = 0; i < rootCount; i++)
        {
            child = rootLayout.getChildAt(i);
            if (child.getClass() == EditText.class)
            {
                dataList.add(new BasicNameValuePair("title",((EditText)child).getText().toString()));
                continue;
            }
            if(child.getClass() == LinearLayout.class)
            {
                // ��ȡ���г��ظ���ѡ������Բ����� ���˳�ѭ��
                linearLayout = (LinearLayout)child;
                childCount = linearLayout.getChildCount();
                break;
            }
        }
        // ����ѭ������һȡ��options
        for(int i = 0; i < childCount; i++ )
        {
            child = linearLayout.getChildAt(i);
            if(child.getClass() == EditText.class)
            {
                fromEditText = ((EditText)child).getText().toString();
                if(fromEditText.length()>0)
                {
                    dataList.add(new BasicNameValuePair("Option",fromEditText));
                }
            }
        }
        // ����������HTTP-POST�������ύͶƱ
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(HostName+"/submit");
        try {
            httppost.setEntity(new UrlEncodedFormEntity(dataList));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �˷�������Ϊ
     * ���ʷ�����, ��ȡ����������˷��ص�������������һ��List������Ϊ�����ķ���ֵ����
     * ����Ϊ����ͳ��ͼ�ṩ����֧��
     * �������������������ֻ�����һ������ ������Ҫ��ʾ�������ID
     * @param QID �����ID
     * @return
     */
    public static List<Pair<String,Float>> getResult(int QID) throws XmlPullParserException, IOException {
        List<Pair<String,Float>> feedback = null;
        Pair<String, Float> tmpPair = null;
        XmlPullParser xmlPullParser = Xml.newPullParser();
        String  tagName = null;
        URL url = null;
        url = new URL(getVoteUrl(QID,true));
        HttpURLConnection dbConnection = null;
        dbConnection = (HttpURLConnection) url.openConnection();
        dbConnection.setDoOutput(true);
        xmlPullParser.setInput(dbConnection.getInputStream(),"utf-8");
        int evnetType = xmlPullParser.getEventType();
        int optionCount = 0;
        int optionState = 0;
        /**
         * �������������ص�XML�ļ�
         * ����һ�������¼������Ĺ���
         * ʱ�����ޣ�û�м��������Ĺ��ܣ�������λ�ο�
         */
        while(evnetType != XmlPullParser.END_DOCUMENT)
        {
            switch (evnetType)
            {
                case XmlPullParser.START_DOCUMENT:
                    feedback =  new ArrayList<Pair<String, Float>>();
                    break;
                case XmlPullParser.START_TAG:
                    tagName = xmlPullParser.getName();
                    if("Count".equals(tagName))
                    {
                        optionCount = Integer.getInteger(xmlPullParser.getAttributeName(0));
                        continue;
                    }
                    if("Option".equals(tagName))
                    {
                        if (feedback != null) {
                            feedback.add(new Pair<String, Float>(xmlPullParser.getAttributeName(0),0f));
                        }
                    }
                    if("WeigthSet".equals(tagName))
                    {
                        if(optionState!=optionCount)
                        {
                            tmpPair = feedback.get(optionState);
                            feedback.set(optionState,Pair.create(tmpPair.first,(Integer.getInteger(xmlPullParser.getAttributeName(0)).floatValue())));
                            optionState++;
                        }
                    }
                    break;
            }
        }
        return feedback;
    }
}
