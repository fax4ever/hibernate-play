/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package fax.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.junit.Test;

public class DataGenTest extends BaseSessionTest{

	@Test
	public void test() {
		AtomicReference<Object> ref = new AtomicReference<>();

		inTransaction( session -> {
			User user = new User();
			session.persist( user );
			assertThat( user ).isNotNull();
			ref.set( user.getLastUpdate() );
		} );

		inTransaction( session -> {
			User load = session.load( User.class, 1L );
			load.setName( "CIAOOO" );
			session.merge( load );

			assertThat( load ).isNotNull();
			assertThat( load.getLastUpdate() ).isEqualTo( ref.get() );
		} );

		inTransaction( session -> {
			User load = session.load( User.class, 1L );

			assertThat( load ).isNotNull();
			assertThat( load.getLastUpdate() ).isEqualTo( ref.get() );
		} );
	}

	@Test
	public void test2() {
		inTransaction( session -> {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaUpdate<User> criteria = builder.createCriteriaUpdate( User.class );
			Root<User> root = criteria.from( User.class );
			criteria.set( root.<Timestamp>get( "moment" ), builder.currentTimestamp() );
			session.createQuery( criteria ).executeUpdate();
		} );
	}

	@Test
	public void test3() {
		inTransaction( session -> {
			User user = new User();
			session.persist( user );
			assertThat( user ).isNotNull();
		} );

		inTransaction( session -> {
			User user = session.load( User.class, 1L );
			assertThat( user ).isNotNull();
		} );
	}
}
