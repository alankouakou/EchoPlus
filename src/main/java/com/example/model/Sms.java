package com.example.model;

public class Sms {
	
	private String from;
	private String to;
	private String text;
	
	public Sms(){
		this.setFrom("Alan");
		this.setTo("");
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	

}
