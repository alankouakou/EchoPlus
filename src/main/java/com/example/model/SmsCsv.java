package com.example.model;

import org.springframework.web.multipart.MultipartFile;

public class SmsCsv {
	
	private String from;
	private MultipartFile file;
	private String text;
	
	public SmsCsv(){
		this.setFrom("Alan");
		this.setText("");
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	

}
