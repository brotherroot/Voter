package com.example.voter;

import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class VoteClass {
	int id;
	String topic;
	String launcher;
	String description;
	boolean is_single;
	boolean has_password;
	String password;
	Time starttime;
	int lefttime;
	int option_count;
	List<Pair<String, Integer>> options;
	
	VoteClass(ViewGroup group) throws Exception {
		id = -1;
		topic = "";
		launcher = "";
		description = "";
		is_single = true;
		has_password = false;
		password = "";
		starttime = null;
		lefttime = 0;
		option_count = 0;
		options = new ArrayList<Pair<String, Integer>>();
		
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
				is_single = R.id.sing == ((RadioGroup)child).getCheckedRadioButtonId();
				break;
			case R.id.sel2:
				has_password = R.id.self == ((RadioGroup)child).getCheckedRadioButtonId();
				if (has_password) {
					// @todo get password
					password = "";
				}
				break;
			case R.id.timestop:
				starttime = new Time("GMT+8");
				lefttime = Integer.parseInt(((TextView)child).getText().toString());
				break;
			case R.id.newvotes_layout:
				ViewGroup curr = (ViewGroup)child;
				View curr_option = null;
				option_count = curr.getChildCount();;
				for (int oi = 0; oi < option_count; ++oi) {
					curr_option = curr.getChildAt(oi);
					if (curr_option.getClass() == TextView.class) {
						options.add(Pair.create(((TextView)curr_option).getText().toString(), 0));
					}
				}
			}
        }
	}
}
