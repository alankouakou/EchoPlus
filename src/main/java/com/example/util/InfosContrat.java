package com.example.util;

import java.util.Date;

public class InfosContrat {
	private String numContrat;
	private String branche;
	private Date dateEcheance;
	
	
	public InfosContrat() {
	}

	public InfosContrat(String numContrat, String branche, Date dateEcheance) {
		super();
		this.numContrat = numContrat;
		this.branche = branche;
		this.dateEcheance = dateEcheance;
	}
	
	
	public String getNumContrat() {
		return numContrat;
	}
	public void setNumContrat(String numContrat) {
		this.numContrat = numContrat;
	}
	public String getBranche() {
		return branche;
	}
	public void setBranche(String branche) {
		this.branche = branche;
	}
	public Date getDateEcheance() {
		return dateEcheance;
	}
	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}
	

}
