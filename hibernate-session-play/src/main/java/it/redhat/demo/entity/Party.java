/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package it.redhat.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Fabio Massimo Ercoli
 */
@Entity
public class Party {

	@Id
	private Integer id;

	private String name;

	private LocalDateTime moment;

	private String location;

	public Party() {
	}

	public Party(Integer id, String name, LocalDateTime moment, String location) {
		this.id = id;
		this.name = name;
		this.moment = moment;
		this.location = location;
	}



}
