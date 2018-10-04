package it.redhat.demo.entity.altperclass;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class NodeLink {

	@Id
	private Integer id;

	@ManyToOne
	private Node source;

	@ManyToOne
	private Node target;

	public NodeLink() {
	}

	public NodeLink(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public void assignSource(Node source) {
		if ( this.source != null ) {
			this.source.getChildren().remove( this );
		}

		setSource( source );
		this.source.getChildren().add( this );
	}

	public void assignTarget(Node target) {
		setTarget( target );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		NodeLink nodeLink = (NodeLink) o;
		return Objects.equals( id, nodeLink.id );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id );
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "NodeLink{" );
		sb.append( "id=" ).append( id );
		sb.append( '}' );
		return sb.toString();
	}
}
