package it.redhat.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ManyToOneBi {

	@Id
	private Integer id;

	@ManyToOne
	private OneToManyBi message;

}
