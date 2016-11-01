package com.example.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.text.StrSubstitutor;

public class SmsTool {

	public static ArrayList<String> addPrefixToNumbers(String destinataires) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(destinataires, ",");

		while (st.hasMoreTokens()) {
			StringBuilder sb = new StringBuilder();
			sb.append("225");
			sb.append(st.nextToken());
			list.add(sb.toString());
		}
		return list;
	}
	
	
	public String formatAvisEcheance(String template, InfosContrat infoContrat){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String,String> params = new HashMap();
		params.put("contrat", infoContrat.getNumContrat());
		params.put("branche",infoContrat.getBranche());
		params.put("dateEcheance", sdf.format(infoContrat.getDateEcheance()));
		StrSubstitutor sub = new StrSubstitutor(params);
		return sub.replace(template);
	}
	
	//Formattage des messages 
	public String formatAvisEcheance(String InfosContrat){
	    Map<String, Object> valueMap = new HashMap<String, Object>();
	    valueMap.put("state", "Andhra Pradesh");
	    valueMap.put("capital", "Hyderabad");
	    
	    String varPrefix = "{";
	    String varSuffix = "}";
	    String template = "The capital of {state} is {capital}";
	    return StrSubstitutor.replace(template, valueMap, varPrefix, varSuffix);
	}
}
