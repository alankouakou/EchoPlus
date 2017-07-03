package com.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Account;
import com.example.model.Cotisation;
import com.example.model.FileBucket;
import com.example.model.Group;
import com.example.model.Person;
import com.example.model.PersonInfo;
import com.example.model.User;
import com.example.service.GroupService;
import com.example.service.MessageService;
import com.example.service.PersonService;
import com.example.service.UserService;
@SessionAttributes("user")
@Controller
public class XlsFileController {
	
	@Autowired
	private Account account;
	
	@Autowired
	private GroupService groupeSvc;
	
	@Autowired
	private PersonService personSvc;

	@Autowired
	private UserService usrSvc;
	
	@Autowired 
	private MessageService messageSvc;
	
	@ModelAttribute("groupes")
	public List<Group> getGroupes(){
		return groupeSvc.findAll();
	}


	//ServletContext context;
	@RequestMapping(value = {"/{group}/import"}, method = RequestMethod.GET)
	public String HelloExcel(Model model, @ModelAttribute("group") Group group) {
		//model.addAttribute("lstUser", lstUser);
		FileBucket fileBucket = new FileBucket();
		fileBucket.setGroupe(group.getId());
		model.addAttribute("fileBucket",fileBucket);
		model.addAttribute("message", "Importer des contacts");
		return "excel_upload";
	}

	
	@RequestMapping(value = {"/import-xls"}, method = RequestMethod.GET)
	public String HelloExcel(Model model) {
		//model.addAttribute("lstUser", lstUser);
		FileBucket fileBucket = new FileBucket();
		model.addAttribute("fileBucket",fileBucket);
		model.addAttribute("message", "Importer des contacts");
		return "excel_upload";
	}

	@RequestMapping(value = "/import-xls", method = RequestMethod.POST)
	public String processExcel2003(@Valid FileBucket fileBucket, BindingResult result, Model model,  RedirectAttributes ra,Principal principal) {
		Set<String> Contacts = new HashSet();
		if(result.hasErrors()){
			ra.addFlashAttribute("message", "Fichier invalide!");
			return "redirect:/import-xls";
		} else {
		try {
			
			List<Person> lstPerson = new ArrayList<>();
			Group groupe = groupeSvc.findOne(fileBucket.getGroupe());
			User user = usrSvc.findByUsername(principal.getName());
			int i = 0;
			// Creates a workbook object from the uploaded excelfile
			HSSFWorkbook workbook = new HSSFWorkbook(fileBucket.getFile().getInputStream());
			// Creates a worksheet object representing the first sheet
			HSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the UserInfo Model
				Person person = new Person();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				//person.setId((int) row.getCell(0).getNumericCellValue());
				person.setLastName(row.getCell(1).getStringCellValue().toUpperCase());
				person.setFirstName(WordUtils.capitalizeFully(row.getCell(2).getStringCellValue()));
				person.setContact(row.getCell(3).getStringCellValue().replaceAll("[^0-9]", ""));
				person.addGroup(groupe);
				person.setUser(user);
				//person.setInputDate(row.getCell(2).getDateCellValue());
				// persist data into database in here
				
				if(!Contacts.contains(person.getContact())){
					lstPerson.add(person);
					Contacts.add(person.getContact());
				}
			}
			workbook.close();
			
			// Enregistrement des contacts en base
			personSvc.save(lstPerson);
			
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
	
	@RequestMapping(value = {"/rappel-cotisations"}, method = RequestMethod.GET)
	public String rappelCotisations(Model model, Principal principal) {
		//model.addAttribute("lstUser", lstUser);
		FileBucket fileBucket = new FileBucket();
		model.addAttribute("fileBucket",fileBucket);
		model.addAttribute("message", "Rappel de cotisations");
		return "cotisations_upload";
	}	

	@RequestMapping(value = "/rappel-cotisations", method = RequestMethod.POST)
	public String rappelCotisation(@Valid FileBucket fileBucket, BindingResult result, Model model,  RedirectAttributes ra) {
		

		
		Set<String> Contacts = new HashSet();
		if(result.hasErrors()){
			ra.addFlashAttribute("message", "Fichier invalide!");
			return "redirect:/rappel-cotisations";
		} else {
		try {
			List<Cotisation> lstCotisations = new ArrayList<>();
			Group groupe = groupeSvc.findOne(fileBucket.getGroupe());
			User user = usrSvc.findByUsername("user");
			int i = 0;
			// Creates a workbook object from the uploaded excelfile
			HSSFWorkbook workbook = new HSSFWorkbook(fileBucket.getFile().getInputStream());
			// Creates a worksheet object representing the first sheet
			HSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the UserInfo Model
				Cotisation cotisation = new Cotisation();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);
				cotisation.setId((long)i);
				System.out.println(row.getCell(1).getStringCellValue());
				cotisation.setLastName(row.getCell(1).getStringCellValue().toUpperCase());
				System.out.println(row.getCell(2).getStringCellValue());
				cotisation.setFirstName(WordUtils.capitalizeFully(row.getCell(2).getStringCellValue()));
				cotisation.setContact(row.getCell(3).getStringCellValue().replaceAll("[^0-9]", ""));
				cotisation.setMontant(row.getCell(4).getNumericCellValue());
				

				//person.setInputDate(row.getCell(2).getDateCellValue());
				// persist data into database in here
				
				if(!Contacts.contains(cotisation.getContact()) && (cotisation.getMontant() > 0)){
					lstCotisations.add(cotisation);
					Contacts.add(cotisation.getContact());
				}
			}
			workbook.close();
			
			// Envoi des relances
			messageSvc.sendRelanceMUDEKO(account,lstCotisations,user);
			
			// Rapport
			model.addAttribute("lstCotisations", lstCotisations);
			model.addAttribute("message",i + " enregistrements trouvés!");
			System.out.println(i + " enregistrements traitées!");			
		} catch (Exception e) {
			e.printStackTrace();
		}
 }
		return "cotisations_upload";
	}
	
/*	
	public String formatMessage(double montant){
		DecimalFormat dcf = new DecimalFormat();
		String message = "Cher frère/soeur. Ta cotisation est de ${montant} F à payer par Orange Money au 07399522, chez Josiane SAMBOLI. Koblata t'attend. SG, Didier KOUAKOU";
		String formattedMessage;
		Map<String,String> data = new HashMap<String,String>();
		data.put("montant", dcf.format(montant));
		formattedMessage = StrSubstitutor.replace(message, data);
		return formattedMessage;
	}
	
*/	
}
