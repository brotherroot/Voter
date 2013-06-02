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

    // 对于android虚拟机来说 10.0.2.2就是本机的localhost
    static String HostName = "http://10.0.2.2:8080";

    /**
     * 此方作用在于返回正确的服务器访问路径
     * @param qid           传入问题的ID
     * @param returnResult  是否同时向服务器请求当前投票结果
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
     * 此方法设计用于从指定的Layout对象中读取子元素，即是用来填写各个选项的EdotText
     * 或者是层嵌套的其他Layout，甄别所属、提取信息并提交到服务端
     * 此方法的能否正确执行在极大程度上依赖于具体用于填写投票表单的布局文件设计
     * 现在假定的布局是，题目存在于传入线性布局器的第一个元素
     * 该线性布局的第二个元素是一个线性布局器
     * 其中容纳所有的投票选项
     * 因此在正是启用这个方法之前
     * ！！！必须根据实际的布局文件进行进步一步的修改！！！
     * ！！！为了应对异常情况，此方法抛出一个异常，必须捕获并处理它！！！
     *
     * @param rootLayout  传入这个方法的LinearLayout对象
     * @throws IOException  方法执行发生错误时抛出的异常
     */
    static void submitVote(LinearLayout rootLayout){

        View child = null;
        LinearLayout linearLayout = null;
        List<NameValuePair> dataList = new ArrayList<NameValuePair>();
        String fromEditText = null;
        int rootCount = rootLayout.getChildCount();
        int childCount = 0;
        // 从布局中提取子元素的方法基本就是这样
        // 以下代码用于提取问题本身的题干
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
                // 获取其中承载各个选项的线性布局器 并退出循环
                linearLayout = (LinearLayout)child;
                childCount = linearLayout.getChildCount();
                break;
            }
        }
        // 在新循环里逐一取得options
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
        // 向服务器提出HTTP-POST请求以提交投票
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
     * 此方法作用为
     * 访问服务器, 获取、解析服务端返回的竖井，并创建一个List对象作为方法的返回值返回
     * 用以为绘制统计图提供数据支持
     * 方便调用起见，这个方法只设计了一个参数 就是需要显示的问题的ID
     * @param QID 问题的ID
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
         * 解析服务器返回的XML文件
         * 这是一个类似事件驱动的过程
         * 时间有限，没有加入完整的功能，仅供诸位参考
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
