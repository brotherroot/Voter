package com.example.voter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class VoteClass implements Serializable {
	private static final long serialVersionUID = 396893752L;
	public static Integer NILID = -1;
	
	private Integer id;
	private String topic;
	private String launcher;
	private String description;
	private Integer lifetime;
	private ArrayList<OptionClass> options;
	private Integer option_num;
	
	public VoteClass() {
		id = NILID;
		topic = "";
		launcher = "";
		description = "";
		lifetime = 0;
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
			case R.id.topic:
				topic = ((TextView)child).getText().toString();
				break;
			case R.id.launcher:
				launcher = ((TextView)child).getText().toString();				
				break;
			case R.id.description:
				description = ((TextView)child).getText().toString();
				break;
			case R.id.lifetime:
				lifetime = Integer.parseInt(((TextView)child).getText().toString());
				break;
			case R.id.option:
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
				if ("Id".equals(tagName)) {
					id = Integer.parseInt(parser.nextText());
				} else if ("Topic".equals(tagName)) {
					topic = parser.nextText();
				} else if ("Launcher".equals(tagName)) {
					launcher = parser.nextText();
				} else if ("Description".equals(tagName)) {
					description = parser.nextText();
				} else if ("Option".equals(tagName)) {				
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

	public Integer getLifeTime() {
		return lifetime;
	}

	public ArrayList<OptionClass> getOptions() {
		return options;
	}

	public Integer getOptionNum() {
		return option_num;
	}
	
	public Integer getTotalWeight() {
		Integer weight = 0;
		for (OptionClass option: options) {	
			weight += option.getWeight();
		}
		return weight;
	}
}
