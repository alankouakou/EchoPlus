package com.example.model;

import javax.validation.constraints.NotNull;

public class Sms {
	@NotNull
	private String from;
	
	private String to;
	private Group group;
	private String text;
	
	public Sms(){
		this.setFrom("Alan");
		this.setTo("");
		this.setGroup(null);
		this.setText("");
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	

}
