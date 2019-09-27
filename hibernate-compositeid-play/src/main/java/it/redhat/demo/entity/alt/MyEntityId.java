package it.redhat.demo.entity.alt;

import java.io.Serializable;
import java.util.Objects;

public class MyEntityId implements Serializable {
	private Long generatedId;
	private Long providedId;

	public MyEntityId(Long generatedId, Long providedId) {
		this.generatedId = generatedId;
		this.providedId = providedId;
	}

	public MyEntityId(Long providedId) {
		this.providedId = providedId;
	}

	private MyEntityId() {
	}

	public Long getGeneratedId() {
		return generatedId;
	}
	public Long getProvidedId() {
		return providedId;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		MyEntityId pk = (MyEntityId) o;
		return Objects.equals( generatedId, pk.generatedId ) &&
				Objects.equals( providedId, pk.providedId );
	}

	@Override
	public int hashCode() {
		return Objects.hash( generatedId, providedId );
	}
}
