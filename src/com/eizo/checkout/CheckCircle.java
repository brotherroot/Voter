package com.eizo.checkout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Pair;
import android.view.View;
import org.xml.sax.ext.Attributes2;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.zip.Adler32;

/**
 * Created by Eizo on 13-5-29.
 * Last Edited by Eizo on 13-5-29.
 */

/**
 * !!!注意!!!
 * 这是一个View组件
 * 直接在一个应该用来呈现统计结果的Activity里：
 * checkCircle =  new CheckCircle( QuestionID )
 * layout.addView(checkCircle)
 * 即可完成添加
 * 基本就是这么简单了....
 * 其中调用了WebAccess
 * 注意在Manifest.xml 文件里提供访问网络的权限请求
 */

@SuppressWarnings("AndroidLintDrawAllocation")
public class CheckCircle extends View {

    /**
     * 以下定义用于控制绘制图形的位置参数
     * 并给出默认值
     * final float IMAGEZONE_WIDTH      绘图区域宽度
     * final float IMAGEZONE_HEIGHT     绘图区域高度
     * final float IMAGEZONE_START_X    绘图区域左上角横坐标
     * final float IMAGEZONE_START_Y    绘图区域左上角纵坐标
     * final float IMAGEZONE_DELTA      绘图区域绘制偏移量
     */

    final float IMAGEZONE_WIDTH = 400f;
    final float IMAGEZONE_HEIGHT = 400f;
    final float IMAGEZONE_START_X = 10f;
    final float IMAGEZONE_START_Y = 10f;
    final float IMAGEZONE_DELTA = 10f;

    /**
     * IMAGEZONE_FILLIMAGE      用于控制图形绘制成环形或者扇形，默认环形
     * IMAGEZONE_StrokeWidth    用于控制画笔粗细
     */

    final boolean IMAGEZONE_FILLIMAGE = false;
    final int IMAGEZONE_StrokeWidth = 15;


    List<Pair<String, Float>> dataList;
    /**
     * 最终封装的构造函数
     * @param context    从创建出传入的context
     * @param questionID 问题ID
     */

    public CheckCircle(Context context,int questionID)
    {
        super(context);
        try {
            // 从网络获取数据
            dataList = WebAccess.getResult(questionID);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 初始化数据列表
        initAngles();
    }

    /**
     * 继承于父类的默认构造函数
     * @param context   从创建出传入的context
     */
    public CheckCircle(Context context)
    {
        super(context);
    }

    /**
     * 重中之重
     * 通过这个重写的onDraw方法实现对于统计图表的绘制
     * 这是一个完成度相对较低的方法
     * 只给出了绘制统计图的方法，没有来及完成图例的绘制部分
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // 默认构造函数
        super.onDraw(canvas);
        // 初始化画布、画笔
        canvas.drawColor(Color.WHITE);
        int ColorSet[] = {Color.BLUE,Color.YELLOW,Color.RED, Color.MAGENTA,Color.GREEN,Color.CYAN};
        int listLength = dataList.size();
        float angleSweeped = 0f;
        Paint pen[] = new Paint[listLength];
        for(int i = 0; i<listLength; i++)
        {
            pen[i] = new Paint();
            pen[i].setAntiAlias(IMAGEZONE_FILLIMAGE);
            pen[i].setColor(ColorSet[i%6]);
            pen[i].setStyle(Paint.Style.STROKE);
            pen[i].setStrokeWidth(IMAGEZONE_StrokeWidth);
        }
        // 分段绘制图形
        for(int i = 0; i<listLength; i++)
        {
            float AR1 = IMAGEZONE_START_X + IMAGEZONE_DELTA;
            float AR2 = IMAGEZONE_START_Y + IMAGEZONE_DELTA;
            float AR3 = IMAGEZONE_WIDTH + AR1;
            float AR4 = IMAGEZONE_HEIGHT + AR2;
            float AR6 = dataList.get(i).second;
            canvas.drawArc(new RectF(AR1, AR2, AR3, AR4),angleSweeped,AR6,false,pen[i]);
            angleSweeped += AR6;
        }
    }

    /**
     * 将来自WebAccess的List处理成对应的字串-角度序列
     */
    void initAngles()
    {
        float sum = 0;
        Pair<String,Float> tmpPair = null;
        for(int i=0; i < dataList.size(); i++)
        {
            sum += dataList.get(i).second;
        }
        for(int i=0; i < dataList.size(); i++)
        {
            tmpPair = dataList.get(i);
            dataList.set(i,Pair.create(tmpPair.first, tmpPair.second * 360.f / sum));
        }
    }
}
