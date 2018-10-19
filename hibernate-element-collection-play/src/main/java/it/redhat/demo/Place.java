package it.redhat.demo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
public class Place {

	@Id
	private String name;

	@ElementCollection
	private List<String> addresses = new ArrayList<>();

	@ElementCollection
	@OrderColumn(columnDefinition = "ordinal")
	private List<String> phone = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}
}
