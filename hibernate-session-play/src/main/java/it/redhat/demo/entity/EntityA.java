package it.redhat.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EntityA {

	@Id
	private String id;

	@OneToOne
	private EntityB b;

}
