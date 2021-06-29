/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package fax.open.demo.entity;

import javax.persistence.Entity;

@Entity
public class Dog extends Animal {

	private Integer bones;

	public Integer getBones() {
		return bones;
	}

	public void setBones(Integer bones) {
		this.bones = bones;
	}
}
