package com.example.controller;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.FileBucket;
import com.example.util.FileValidator;

@Controller
public class FileUploadController {
	
	private static String UPLOAD_LOCATION="/upload/";
	
	@Autowired
	private FileValidator fileValidator;
	
	@InitBinder("fileBucket")
	private void initBinderFileBucket(WebDataBinder binder){
		binder.setValidator(fileValidator);
	}
	
	@RequestMapping(value="/composesv",method=RequestMethod.GET)
	public String composeSmsWithCSV(ModelMap model){
		FileBucket fileBucket= new FileBucket();
		model.addAttribute("fileBucket", fileBucket);
		return "compose_csv";
	}
	
	@RequestMapping(value="/composesv",method=RequestMethod.POST)
	public String sendSmsWithCSV(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
		if(result.hasErrors()){
			System.out.println("validation errors");
			return "compose_csv";
		} else {
			MultipartFile multipartFile = fileBucket.getFile();
			//Process the file
			System.out.println("File processing...");
			FileCopyUtils.copy(fileBucket.getFile().getBytes(),new File(UPLOAD_LOCATION+fileBucket.getFile().getOriginalFilename()));
			
			String fileName = multipartFile.getOriginalFilename();
			model.addAttribute("fileName", fileName);
			return "confirmedCSV";
		}
	}
	

}
