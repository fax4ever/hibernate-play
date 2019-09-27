package it.redhat.demo.entity.alt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity(name = "MyEntity")
@Table(name = "MY_ENTITY")
@IdClass( MyEntityId.class )
public class MyEntity {

	@Id
	private Long providedId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long generatedId;

	private String notes;

	public MyEntityId getId() {
		return new MyEntityId( generatedId, providedId );
	}

	public void setId(MyEntityId id) {
		this.generatedId = id.getGeneratedId();
		this.providedId = id.getProvidedId();
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
