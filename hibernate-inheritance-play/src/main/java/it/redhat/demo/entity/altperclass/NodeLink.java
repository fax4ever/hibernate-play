package it.redhat.demo.entity.altperclass;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// taken from Hibernate OGM project
// https://github.com/hibernate/hibernate-ogm
@Entity
public class NodeLink {

	@Id
	private String id;

	@ManyToOne
	private Node source;

	@ManyToOne
	private Node target;
}
