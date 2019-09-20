package it.redhat.demo.entity;

import java.io.Serializable;
import java.util.Objects;

public class EventMilestonePK implements Serializable {

	private Long eventId;
	private Long eventMilestoneId;

	public EventMilestonePK(Long eventId, Long eventMilestoneId) {
		this.eventId = eventId;
		this.eventMilestoneId = eventMilestoneId;
	}

	public EventMilestonePK(Long eventId) {
		this.eventId = eventId;
	}

	private EventMilestonePK() {
	}

	public Long getEventId() {
		return eventId;
	}

	public Long getEventMilestoneId() {
		return eventMilestoneId;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		EventMilestonePK that = (EventMilestonePK) o;
		return Objects.equals( eventId, that.eventId ) &&
				Objects.equals( eventMilestoneId, that.eventMilestoneId );
	}

	@Override
	public int hashCode() {
		return Objects.hash( eventId, eventMilestoneId );
	}
}
