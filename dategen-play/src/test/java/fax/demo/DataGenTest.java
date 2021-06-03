/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package fax.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;

import org.junit.Test;

public class DataGenTest extends BaseSessionTest{

	@Test
	public void test() {
		inTransaction( session -> {
			User user = new User();
			session.persist( user );

//			CriteriaBuilder builder = session.getCriteriaBuilder();
//			CriteriaUpdate<User> criteria = builder.createCriteriaUpdate( User.class );
//			criteria.set( "moment", builder.currentTimestamp() );
//			session.createQuery( criteria ).executeUpdate();

			assertThat( user ).isNotNull();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<java.sql.Timestamp> criteria = builder.createQuery( java.sql.Timestamp.class );
			criteria.from( User.class );
			criteria.select( builder.currentTimestamp() );
			List<Timestamp> resultList = session.createQuery( criteria ).getResultList();

			assertThat( resultList ).isNotEmpty();
		} );
	}
}
