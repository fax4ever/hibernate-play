package it.redhat.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Condominium {

	private String id;
	private int size;

	Condominium() {
	}

	public Condominium(String id, int size) {
		this.id = id;
		this.size = size;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
