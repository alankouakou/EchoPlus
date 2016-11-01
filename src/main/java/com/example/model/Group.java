package com.example.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="groupe")
public class Group implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String designation;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="groups")
	private Set<Person> membres;
	
	public Set<Person> getMembres() {
		return membres;
	}
	
	public void setMembres(Set<Person> persons){
		this.membres = persons;
	}
	
	public void removeMembre(Person person){
		this.membres.remove(person);
	}
	
	public void addMembre(Person membre) {
		membre.addGroup(this);
		membres.add(membre);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}

}
