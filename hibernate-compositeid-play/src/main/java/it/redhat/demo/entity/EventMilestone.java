package it.redhat.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * This is taken from an official Hibernate Issue.
 * See <a>https://hibernate.atlassian.net/browse/HHH-13623</a>
 */
@Entity
@Table(name = "EVNT_MILSTN")
@IdClass(EventMilestonePK.class)
public class EventMilestone {

	@Id
	@Column(name = "EVNT_ID")
	private Long eventId;

	@Id
	@Column(name = "EVNT_MILSTN_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventMilestoneId;

	public EventMilestonePK getId() {
		return new EventMilestonePK( eventId, eventMilestoneId );
	}

	public void setId(EventMilestonePK id) {
		this.eventId = id.getEventId();
		this.eventMilestoneId = id.getEventMilestoneId();
	}
}
