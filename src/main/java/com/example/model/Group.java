package com.example.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="groupe")
public class Group implements Serializable {
	


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name="account")
	private User user;	
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="groups")
	private Set<Person> members;
	
	public Set<Person> getMembers() {
		return members;
	}
	
	public void setMembers(Set<Person> persons){
		this.members = persons;
	}
	
	public void removeMember(Person person){
		this.members.remove(person);
	}
	
	public void addMember(Person member) {
		members.add(member);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return  name ;
	}
}
