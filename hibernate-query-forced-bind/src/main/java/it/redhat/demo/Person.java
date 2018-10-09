package it.redhat.demo;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	private Integer id;

	@ElementCollection
	private Map<Integer, String> localized = new HashMap<Integer, String>();

	public Person() {
	}

	public Person(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<Integer, String> getLocalized() {
		return localized;
	}

	public void setLocalized(Map<Integer, String> localized) {
		this.localized = localized;
	}
}
