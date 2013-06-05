package com.example.voter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Visualization extends View {

	/**
	 * 以下定义用于控制绘制图形的位置参数 并给出默认值 final float IMAGEZONE_WIDTH 绘图区域宽度 final float
	 * IMAGEZONE_HEIGHT 绘图区域高度 final float IMAGEZONE_START_X 绘图区域左上角横坐标 final
	 * float IMAGEZONE_START_Y 绘图区域左上角纵坐标 final float IMAGEZONE_DELTA 绘图区域绘制偏移量
	 */

	final float IMAGEZONE_WIDTH = 400f;
	final float IMAGEZONE_HEIGHT = 400f;
	final float IMAGEZONE_START_X = 10f;
	final float IMAGEZONE_START_Y = 10f;
	final float IMAGEZONE_DELTA = 10f;
	final float EXPLEZONE_WIDTH = 400f;
	final float EXPLEZONE_DELTA = 20f;
	final float EXPLEZONE_START_Y = IMAGEZONE_START_Y + IMAGEZONE_HEIGHT
			+ EXPLEZONE_DELTA;
	final float EXPLEZONE_CELL_SZIE = 30f;

	/**
	 * IMAGEZONE_FILLIMAGE 用于控制图形绘制成环形或者扇形，默认环形 IMAGEZONE_StrokeWidth 用于控制画笔粗细
	 */

	final boolean IMAGEZONE_FILLIMAGE = false;
	final int IMAGEZONE_StrokeWidth = 15;
	final int EXPLEZONE_StrokeWidth = 5;
	final int EXPLEZONE_TEXT_StrokeWidth = 2;
	final boolean AA = true; 
	List<Pair<String, Float>> dataList;

	/**
	 * 最终封装的构造函数
	 * 
	 * @param context
	 *            从创建出传入的context
	 * @param questionID
	 *            问题ID
	 */

	public Visualization(Context context, int questionID) {
		super(context);
		// 初始化数据列表
		dataList = new ArrayList<Pair<String, Float>>();
		initDataList(questionID);
		initAngles();
	}

	/**
	 * 继承于父类的默认构造函数
	 * 
	 * @param context
	 *            从创建出传入的context
	 */
	public Visualization(Context context) {
		super(context);
	}

	/**
	 * 重中之重 通过这个重写的onDraw方法实现对于统计图表的绘制 这是一个完成度相对较低的方法 只给出了绘制统计图的方法，没有来及完成图例的绘制部分
	 * 
	 * @param canvas
	 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// 默认构造函数
		super.onDraw(canvas);
		// 初始化画布、画笔
		canvas.drawColor(Color.WHITE);
		int ColorSet[] = { Color.rgb(0xfa,0xda,0x8d),
				Color.rgb(0xde,0x7d,0x2c),
				Color.rgb(0xb3,0xa8,0x96),
				Color.rgb(0x03,0x26,0x3a),Color.BLUE, Color.YELLOW, Color.RED, Color.MAGENTA,
				Color.GREEN, Color.CYAN };
		int listLength = dataList.size();
		Log.e("listLength", "" + listLength);
		float angleSweeped = 0f;
		Paint tpen = new Paint();
		tpen.setAntiAlias(AA);
		tpen.setColor(Color.BLACK);
		tpen.setStyle(Paint.Style.STROKE);
		tpen.setStrokeWidth(2);
		tpen.setTextSize(24);
		
		Paint pen[] = new Paint[listLength];
		for (int i = 0; i < listLength; i++) {
			pen[i] = new Paint();
			pen[i].setAntiAlias(AA);
			pen[i].setColor(ColorSet[i % ColorSet.length]);
			pen[i].setStyle(Paint.Style.FILL);
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
            canvas.drawArc(new RectF(AR1, AR2, AR3, AR4),angleSweeped,AR6,true,pen[i]);
            angleSweeped += AR6;
        }

		// 绘制图例
		for (int i = 0; i < listLength; i++) {
			pen[i].setStrokeWidth(EXPLEZONE_StrokeWidth);
			float AR1 = IMAGEZONE_START_X + IMAGEZONE_DELTA;
			float AR2 = EXPLEZONE_START_Y + EXPLEZONE_DELTA + (EXPLEZONE_DELTA+EXPLEZONE_CELL_SZIE)*i;
			float AR3 = EXPLEZONE_CELL_SZIE + AR1;
			float AR4 = EXPLEZONE_CELL_SZIE + AR2;
			canvas.drawRect(AR1, AR2, AR3, AR4, pen[i]);
			Log.e("TEXT",dataList.get(i).first);
			canvas.drawText(voteRate(i), AR3+EXPLEZONE_DELTA*3/2, (AR2+AR4)/2, tpen);
		}
	}

	/**
	 * 将来自WebAccess的List处理成对应的字串-角度序列
	 */
	void initAngles() {
		float sum = 0;
		Pair<String, Float> tmpPair = null;
		Pair<String, Float> newPair = null;
		for (int i = 0; i < dataList.size(); i++) {
			sum += dataList.get(i).second;
		}
		for (int i = 0; i < dataList.size(); i++) {
			tmpPair = dataList.get(i);
			newPair = Pair.create(tmpPair.first, tmpPair.second * 360.f / sum);
			Log.e("ang", newPair.second.toString());
			dataList.set(i, newPair);
		}
	}

	void initDataList(int QID) {

		XmlPullParser parser = null;
		VoteClass voteClass = null;
		try {
			parser = WebAccess.getXML(WebAccess.getRequestUrl(QID));
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			voteClass = new VoteClass(parser);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadIdError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 数据初始化
		List<OptionClass> oplist = voteClass.getOptions();
		OptionClass tmp = null;
		for (int i = 0; i < oplist.size(); i++) {
			tmp = oplist.get(i);
			dataList.add(new Pair<String, Float>(tmp.getOption(), tmp
					.getWeight().floatValue()));
		}
	}

	String voteRate(int ID)
	{
		String newString = dataList.get(ID).first;
		newString += "   " ;
		newString += (dataList.get(ID).second/3.6)+"%";
		return newString;
	}
}
