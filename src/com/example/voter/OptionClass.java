package com.example.voter;

import java.io.Serializable;

public class OptionClass implements Serializable {
	private static final long serialVersionUID = 131201L;
	private String option;
	private Integer weight;
	
	public OptionClass() {
		option = "";
		weight = 0;
	}
	
	public OptionClass(String str, Integer num) {
		option = str;
		weight = num;
	}
	
	public OptionClass(OptionClass op) {
		option = op.option;
		weight = op.weight;
	}
	
	static OptionClass create(String str) {
		return new OptionClass(str);
	}
	
	public String getOption() {
		return option;
	}

	public Integer getWeight() {
		return weight;
	}

	public OptionClass(String str) {
		option = str;
		weight = 0;
	}
}
