package it.redhat.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityB {

	@Id
	private Long id;

	private String name;

}
