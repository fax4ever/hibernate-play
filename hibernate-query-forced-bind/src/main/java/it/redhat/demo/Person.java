package it.redhat.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	private Integer petsOwned;

	public Person() {
	}

	public Person(String name, Integer petsOwned) {
		this.name = name;
		this.petsOwned = petsOwned;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
