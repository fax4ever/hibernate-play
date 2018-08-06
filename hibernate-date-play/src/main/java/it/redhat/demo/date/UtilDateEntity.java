package it.redhat.demo.date;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UtilDateEntity {

	@Id
	private Integer id;

	private String name;

	@Temporal( TemporalType.DATE )
	private Date day;

	@Temporal( TemporalType.TIME )
	private Date time;

	@Temporal( TemporalType.TIMESTAMP )
	private Date moment;

	public UtilDateEntity() {
	}

	public UtilDateEntity(Integer id, String name) {
		this.id = id;
		this.name = name;

		this.moment = new Date();
		this.day = moment;
		this.time = moment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}
}
