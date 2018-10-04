package it.redhat.demo.entity.altperclass;

import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class TextNode extends Node {

	private String text;

	public TextNode() {
	}

	public TextNode(Integer id, String name, String text) {
		super( id, name );
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		TextNode textNode = (TextNode) o;
		return Objects.equals( text, textNode.text ) &&
				Objects.equals( id, textNode.id ) &&
				Objects.equals( name, textNode.name ) &&
				Objects.equals( children, textNode.children );
	}

	@Override
	public int hashCode() {
		return Objects.hash( text, id, name, children );
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "TextNode{" );
		sb.append( "text='" ).append( text ).append( '\'' );
		sb.append( ", id=" ).append( id );
		sb.append( ", name='" ).append( name ).append( '\'' );
		sb.append( ", children=" ).append( children );
		sb.append( '}' );
		return sb.toString();
	}
}
