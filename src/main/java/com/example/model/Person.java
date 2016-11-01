package com.example.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Person implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String contact;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="group_contact",joinColumns={ @JoinColumn(name="person_id")} ,inverseJoinColumns= { @JoinColumn(name="group_id")})
	private Set<Group> groups;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	public void addGroup(Group group){
		groups.add(group);
		group.addMembre(this);
	}
	
	public void removeGroup(Group group){
		groups.remove(group);
	}
	
	public void setGroups(Set<Group> groupes){
		this.groups=groupes;
	}
	
	public Set<Group> getGroups(){
		return this.groups;
	}

}
