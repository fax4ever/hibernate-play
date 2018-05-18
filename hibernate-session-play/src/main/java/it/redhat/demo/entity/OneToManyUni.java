package it.redhat.demo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class OneToManyUni {

	@Id
	private Integer id;

	@OneToMany(cascade = CascadeType.ALL)
	private List<NoRel> messages = new ArrayList<>();

}
