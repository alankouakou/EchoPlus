package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.FileBucket;
import com.example.model.PersonInfo;

@Controller
public class XlsFileController {


	//ServletContext context;

	@RequestMapping(value = {"/import-xls"}, method = RequestMethod.GET)
	public String HelloExcel(Model model) {
		//model.addAttribute("lstUser", lstUser);
		FileBucket fileBucket = new FileBucket();
		model.addAttribute("fileBucket",fileBucket);
		model.addAttribute("message", "Importer des contacts");
		return "excel_upload";
	}

	@RequestMapping(value = "/import-xls", method = RequestMethod.POST)
	public String processExcel2003(@Valid FileBucket fileBucket, BindingResult result, Model model) {
		if(result.hasErrors()){
			model.addAttribute("message", "Fichier invalide!");
			return "redirect:/import-xls";
		} else {
		try {
			List<PersonInfo> lstPerson = new ArrayList<>();
			int i = 0;
			// Creates a workbook object from the uploaded excelfile
			HSSFWorkbook workbook = new HSSFWorkbook(fileBucket.getFile().getInputStream());
			// Creates a worksheet object representing the first sheet
			HSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the UserInfo Model
				PersonInfo person = new PersonInfo();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				person.setId((int) row.getCell(0).getNumericCellValue());
				person.setFirstName(row.getCell(1).getStringCellValue());
				person.setLastName(row.getCell(2).getStringCellValue());
				person.setContact(row.getCell(3).getStringCellValue());
				//person.setInputDate(row.getCell(2).getDateCellValue());
				// persist data into database in here
				lstPerson.add(person);
			}
			workbook.close();
			model.addAttribute("lstPerson", lstPerson);
			model.addAttribute("message",i + " contacts trouvés!");
			System.out.println(i + " contacts trouvés!");			
		} catch (Exception e) {
			e.printStackTrace();
		}
 }
		return "excel_upload";
	}

	@RequestMapping(value = "/import-xls2007", method = RequestMethod.POST)
	public String processExcel2007(@Valid FileBucket fileBucket, BindingResult result, Model model) {
		if(result.hasErrors()){
			model.addAttribute("message", "Fichier invalide!");
			return "redirect:/import-xls2007";
		} else {
		try {
			List<PersonInfo> lstPerson = new ArrayList<>();
			int i = 0;
			// Creates a workbook object from the uploaded excelfile
			XSSFWorkbook workbook = new XSSFWorkbook(fileBucket.getFile().getInputStream());
			// Creates a worksheet object representing the first sheet
			XSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the UserInfo Model
				PersonInfo person = new PersonInfo();
				// Creates an object representing a single row in excel
				XSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				person.setId((int) row.getCell(0).getNumericCellValue());
				person.setFirstName(row.getCell(1).getStringCellValue());
				person.setLastName(row.getCell(2).getStringCellValue());
				person.setContact(row.getCell(3).getStringCellValue());
				// persist data into database in here
				lstPerson.add(person);
			}
			workbook.close();
			model.addAttribute("lstPerson", lstPerson);
			model.addAttribute("message",i + " contacts trouvés!");
			System.out.println(i + " contacts trouvés!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return "excel_upload";
	}

}
