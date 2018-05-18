package it.redhat.demo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class OneToManyJoin {

	@Id
	private Integer id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ciao")
	private List<NoRel> messages = new ArrayList<>();

}
