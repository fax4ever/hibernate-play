package it.redhat.demo.orderby;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Landmark {

	@Id
	private String name;

	@OneToMany
	@OrderBy("nick")
	private List<Visitor> visitors = new ArrayList<>();

	public Landmark() {
	}

	public Landmark(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Visitor> getVisitors() {
		return visitors;
	}

	public void visit(Visitor visitor) {
		this.visitors.add( visitor );
	}
}
