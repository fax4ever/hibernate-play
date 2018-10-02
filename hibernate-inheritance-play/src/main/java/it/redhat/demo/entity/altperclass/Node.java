package it.redhat.demo.entity.altperclass;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Node {

	@Id
	private String id;

	@OneToMany(mappedBy = "source")
	private Set<NodeLink> children = new HashSet<>();
}
