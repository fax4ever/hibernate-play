package it.redhat.demo.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LocalDateEntity {

	@Id
	private Integer id;

	private String name;

	private LocalDate day;

	private LocalTime time;

	private LocalDateTime moment;

	public LocalDateEntity() {
	}

	public LocalDateEntity(Integer id, String name) {
		this.id = id;
		this.name = name;

		this.day = LocalDate.now();
		this.time = LocalTime.now();
		this.moment = LocalDateTime.of(day, time);
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

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalDateTime getMoment() {
		return moment;
	}

	public void setMoment(LocalDateTime moment) {
		this.moment = moment;
	}
}
