package it.redhat.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Master {

	@Id
	private Integer id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private Servant servant;

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

	public Servant getServant() {
		return servant;
	}

	public void setServant(Servant servant) {
		this.servant = servant;
	}
}
