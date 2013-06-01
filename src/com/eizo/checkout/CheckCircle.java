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
 * !!!ע��!!!
 * ����һ��View���
 * ֱ����һ��Ӧ����������ͳ�ƽ����Activity�
 * checkCircle =  new CheckCircle( QuestionID )
 * layout.addView(checkCircle)
 * ����������
 * ����������ô����....
 * ���е�����WebAccess
 * ע����Manifest.xml �ļ����ṩ���������Ȩ������
 */

@SuppressWarnings("AndroidLintDrawAllocation")
public class CheckCircle extends View {

    /**
     * ���¶������ڿ��ƻ���ͼ�ε�λ�ò���
     * ������Ĭ��ֵ
     * final float IMAGEZONE_WIDTH      ��ͼ������
     * final float IMAGEZONE_HEIGHT     ��ͼ����߶�
     * final float IMAGEZONE_START_X    ��ͼ�������ϽǺ�����
     * final float IMAGEZONE_START_Y    ��ͼ�������Ͻ�������
     * final float IMAGEZONE_DELTA      ��ͼ�������ƫ����
     */

    final float IMAGEZONE_WIDTH = 400f;
    final float IMAGEZONE_HEIGHT = 400f;
    final float IMAGEZONE_START_X = 10f;
    final float IMAGEZONE_START_Y = 10f;
    final float IMAGEZONE_DELTA = 10f;

    /**
     * IMAGEZONE_FILLIMAGE      ���ڿ���ͼ�λ��Ƴɻ��λ������Σ�Ĭ�ϻ���
     * IMAGEZONE_StrokeWidth    ���ڿ��ƻ��ʴ�ϸ
     */

    final boolean IMAGEZONE_FILLIMAGE = false;
    final int IMAGEZONE_StrokeWidth = 15;


    List<Pair<String, Float>> dataList;
    /**
     * ���շ�װ�Ĺ��캯��
     * @param context    �Ӵ����������context
     * @param questionID ����ID
     */

    public CheckCircle(Context context,int questionID)
    {
        super(context);
        try {
            // �������ȡ����
            dataList = WebAccess.getResult(questionID);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ��ʼ�������б�
        initAngles();
    }

    /**
     * �̳��ڸ����Ĭ�Ϲ��캯��
     * @param context   �Ӵ����������context
     */
    public CheckCircle(Context context)
    {
        super(context);
    }

    /**
     * ����֮��
     * ͨ�������д��onDraw����ʵ�ֶ���ͳ��ͼ��Ļ���
     * ����һ����ɶ���Խϵ͵ķ���
     * ֻ�����˻���ͳ��ͼ�ķ�����û���������ͼ���Ļ��Ʋ���
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Ĭ�Ϲ��캯��
        super.onDraw(canvas);
        // ��ʼ������������
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
        // �ֶλ���ͼ��
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
     * ������WebAccess��List����ɶ�Ӧ���ִ�-�Ƕ�����
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
