package it.redhat.demo.orderby;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Visitor {

	@Id
	private String nick;

	public Visitor() {
	}

	public Visitor(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Visitor visitor = (Visitor) o;
		return Objects.equals( nick, visitor.nick );
	}

	@Override
	public int hashCode() {
		return Objects.hash( nick );
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder( "Visitor{" );
		sb.append( "nick='" ).append( nick ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}
