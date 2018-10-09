package it.redhat.demo;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

@Entity
public class Document {

	@Id
	private Integer id;

	@OneToMany
	@CollectionTable
	@MapKeyColumn(name = "position")
	private Map<Integer, Person> people = new HashMap<>();

	public Document() {
	}

	public Document(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<Integer, Person> getPeople() {
		return people;
	}

	public void setPeople(Map<Integer, Person> people) {
		this.people = people;
	}
}
