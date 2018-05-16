package it.redhat.demo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class CondominiumBuilding {

	private String id;
	private List<Condominium> condominiums = new ArrayList<>();

	CondominiumBuilding() {
	}

	public CondominiumBuilding(String id) {
		this.id = id;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@OrderColumn(name = "condoNo")
	public List<Condominium> getCondominiums() {
		return condominiums;
	}

	public void setCondominiums(List<Condominium> condominiums) {
		this.condominiums = condominiums;
	}
}
