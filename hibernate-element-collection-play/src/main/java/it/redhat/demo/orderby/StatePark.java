package it.redhat.demo.orderby;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class StatePark {

	@Id
	private String name;

	@OneToMany
	private List<Visitor> visitors = new ArrayList<>();

	public StatePark() {
	}

	public StatePark(String name) {
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
