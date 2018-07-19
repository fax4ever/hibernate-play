package it.redhat.demo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Master {

	@Id
	private Integer id;

	private String name;

	@OneToMany(mappedBy = "master")
	private List<Servant> servants = new ArrayList<>();

	public Master() {
	}

	public Master(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Servant> getServants() {
		return servants;
	}

	public void setServants(List<Servant> servants) {
		this.servants = servants;
	}
}
