package com.example.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.example.model.Cotisation;

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

	public static String addPrefixToNumber(String destinataire) {
		return "225" + destinataire;
	}

	public static String formatRelanceCotisationMUDEKO( Cotisation cotisation) {
		DecimalFormat dcf = new DecimalFormat();
		Map<String, String> params = new HashMap<String, String>();
		String template = "Cher frère/soeur. Ta cotisation est de ${montant} F à payer par Orange Money au 07399522, chez Josiane SAMBOLI. Koblata t'attend. SG, Didier KOUAKOU";
		params.put("montant", dcf.format(cotisation.getMontant()));
		StrSubstitutor sub = new StrSubstitutor(params);
		return sub.replace(template);
	}

	public String formatAvisEcheance(String template, InfosContrat infoContrat) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, String> params = new HashMap<String, String>();
		params.put("contrat", infoContrat.getNumContrat());
		params.put("branche", infoContrat.getBranche());
		params.put("dateEcheance", sdf.format(infoContrat.getDateEcheance()));
		StrSubstitutor sub = new StrSubstitutor(params);
		return sub.replace(template);
	}

	// Formattage des messages
	public String formatAvisEcheance(String InfosContrat) {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("state", "Andhra Pradesh");
		valueMap.put("capital", "Hyderabad");

		String varPrefix = "{";
		String varSuffix = "}";
		String template = "The capital of {state} is {capital}";
		return StrSubstitutor.replace(template, valueMap, varPrefix, varSuffix);
	}
}
