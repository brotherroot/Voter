package com.example.voter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class VoteClass implements Serializable {
	private static final long serialVersionUID = 396893752L;
	public static Integer NILID = -1;
	public static Boolean SINGLE = Boolean.TRUE;
	public static Boolean MULTI = Boolean.FALSE;
	
	private Integer id;
	private String topic;
	private String launcher;
	private String description;
	private Boolean single;
	private Boolean encrypted;
	private String password;
	//private Time start_time;
	private Integer left_time;
	private ArrayList<OptionClass> options;
	private Integer option_num;
	
	public VoteClass() {
		id = NILID;
		topic = "";
		launcher = "";
		description = "";
		single = SINGLE;
		encrypted = Boolean.FALSE;
		password = "";
		//start_time = null;
		left_time = 0;
		options = new ArrayList<OptionClass>();
		option_num = options.size();
	}
	
	public VoteClass(ViewGroup group) {
		this();
		View child = null;
		int child_count = group.getChildCount();
		for (int ci = 0; ci < child_count; ++ci) {
			child = group.getChildAt(ci);
			switch (child.getId()) {
			case R.id.titlebox:
				topic = ((TextView)child).getText().toString();
				break;
			case R.id.addid:
				launcher = ((TextView)child).getText().toString();				
				break;
			case R.id.descriptionbox:
				description = ((TextView)child).getText().toString();
				break;
			case R.id.sel1:
				single = (R.id.sing == ((RadioGroup)child).getCheckedRadioButtonId());
				break;
			case R.id.sel2:
				encrypted = (R.id.self == ((RadioGroup)child).getCheckedRadioButtonId());
				if (encrypted) {
					// TODO get password
					password = "";
				}
				break;
			case R.id.timestop:
				//start_time = new Time("GMT+8");
				left_time = Integer.parseInt(((TextView)child).getText().toString());
				break;
			case R.id.newvotes_layout:
				ViewGroup curr = (ViewGroup)child;
				View curr_option = null;
				option_num = curr.getChildCount();
				for (int oi = 0; oi < option_num; ++oi) {
					curr_option = curr.getChildAt(oi);
					if (curr_option.getClass() == EditText.class) {
						options.add(new OptionClass(((EditText)curr_option).getText().toString()));
					}
				}
			}
        }
	}

	public VoteClass(XmlPullParser parser) throws XmlPullParserException, IOException, BadIdError {
		this();
		for (
			int eventType=parser.getEventType();
			eventType!=XmlPullParser.END_DOCUMENT;
			eventType=parser.next()
		) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				
				Log.d("VoteClass", tagName);
				
				if ("Id".equals(tagName)) {
					id = Integer.parseInt(parser.nextText());
				} else if ("Topic".equals(tagName)) {
					topic = parser.nextText();
				} else if ("Launcher".equals(tagName)) {
					launcher = parser.nextText();
				} else if ("Description".equals(tagName)) {
					description = parser.nextText();
				} else if ("Type".equals(tagName)) {
					single = (parser.nextText() == "single");
				} else if ("Password".equals(tagName)) {
					password = parser.nextText();
					encrypted = (password != "");
//				} else if ("LeftTime".equals(tagName)) {
//					left_time = Integer.parseInt(parser.nextText());
				} else if ("Option".equals(tagName)) {
					
					Log.d("VoteClass", parser.getAttributeValue(0));
					
					Integer weight = Integer.parseInt(parser.getAttributeValue(0));
					options.add(new OptionClass(parser.nextText(), weight));
				} else if ("Error".equals(tagName)) {
					throw new BadIdError();
				}
				break;
			}
		}
		option_num = options.size();
	}
	
	public Integer getId() {
		return id;
	}

	public String getTopic() {
		return topic;
	}

	public String getLauncher() {
		return launcher;
	}

	public String getDescription() {
		return description;
	}

	public boolean isSingle() {
		return single;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public String getPassword() {
		return password;
	}

//	public Time getStartTime() {
//		return start_time;
//	}

	public Integer getLeftTime() {
		return left_time;
	}

	public ArrayList<OptionClass> getOptions() {
		return options;
	}

	public Integer getOptionNum() {
		return option_num;
	}
}
