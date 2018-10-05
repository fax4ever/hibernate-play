package it.redhat.demo.entity.altperclass;

import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class SimpleNode extends Node {

	public SimpleNode() {
	}

	public SimpleNode(Integer id, String name) {
		super( id, name );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		SimpleNode that = (SimpleNode) o;
		return Objects.equals( id, that.id ) &&
				Objects.equals( name, that.name );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id, name );
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "SimpleNode{" );
		sb.append( "id=" ).append( id );
		sb.append( ", name='" ).append( name ).append( '\'' );
		sb.append( ", children=" ).append( children );
		sb.append( '}' );
		return sb.toString();
	}
}
