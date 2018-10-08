package it.redhat.demo;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue
	private Integer id;

	@ElementCollection
	private Map<Integer, String> localized = new HashMap<Integer, String>();

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
