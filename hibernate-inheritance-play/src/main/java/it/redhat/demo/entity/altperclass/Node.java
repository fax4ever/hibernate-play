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
	protected Integer id;
	protected String name;

	@OneToMany(mappedBy = "source")
	protected Set<NodeLink> children = new HashSet<>();

	public Node() {
	}

	public Node(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<NodeLink> getChildren() {
		return children;
	}

	public void setChildren(Set<NodeLink> children) {
		this.children = children;
	}
}
