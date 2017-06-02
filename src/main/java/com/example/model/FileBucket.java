package com.example.model;

import org.springframework.web.multipart.MultipartFile;

public class FileBucket {
	
	private MultipartFile file;
	private long groupe;

	public long getGroupe() {
		return groupe;
	}

	public void setGroupe(long groupe) {
		this.groupe = groupe;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
