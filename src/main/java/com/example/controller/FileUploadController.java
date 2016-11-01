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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.FileBucket;
import com.example.util.FileValidator;

@Controller
public class FileUploadController {

	private static String UPLOAD_LOCATION = "G:/uploads/";

	@Autowired
	private FileValidator fileValidator;

	@InitBinder("fileBucket")
	private void initBinderFileBucket(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}

	@RequestMapping(value = "/singleUpload", method = RequestMethod.GET)
	public String composeSmsWithCSV(ModelMap model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
		return "singleFileUploader";
	}

	@RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
	public String singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model)
			throws IOException {
		if (result.hasErrors()) {
			return "redirect:/singleUpload";
		} else {
			MultipartFile multipartFile = fileBucket.getFile();

			System.out.println("File processing...");
			FileCopyUtils.copy(fileBucket.getFile().getBytes(),
					new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
			String fileName = multipartFile.getOriginalFilename();
			model.addAttribute("fileName", fileName);
			return "success";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleUpload(@Valid @RequestParam("file") FileBucket fileBucket, BindingResult result, ModelMap model)
			throws IOException {
		if (result.hasErrors()) {
			System.out.println("validation errors: " + result.toString());
			return "singleFileUploader";
		} else {
			System.out.println("File processing...");
			MultipartFile multipartFile = fileBucket.getFile();
			FileCopyUtils.copy(fileBucket.getFile().getBytes(),
					new File(UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
			String fileName = multipartFile.getOriginalFilename();
			model.addAttribute("fileName", fileName);
			return "success";
		}
	}

}
