package it.redhat.demo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Festival {

	@Id
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Party> parties = new ArrayList<>();

	public Festival() {
	}

	public Festival(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Party> getParties() {
		return parties;
	}

	public void add(Party party) {
		this.parties.add( party );
	}
}
