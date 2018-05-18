package it.redhat.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OneToOneUni {

	@Id
	private Integer id;

	@OneToOne
	private NoRel message;

}
