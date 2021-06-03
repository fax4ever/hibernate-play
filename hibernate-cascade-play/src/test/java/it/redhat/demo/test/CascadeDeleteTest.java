package it.redhat.demo.test;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import org.junit.Test;

import it.redhat.demo.entity.Leader;
import it.redhat.demo.entity.Follower;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

public class CascadeDeleteTest extends BaseSessionTest {

	@Test
	public void test() {
		final Follower follower = new Follower();
		follower.setId( 1 );
		follower.setName( "S" );

		final Leader leaderA = new Leader();
		leaderA.setId( 2 );
		leaderA.setName( "A" );
		leaderA.setFollower( follower );

		final Leader leaderB = new Leader();
		leaderB.setId( 3 );
		leaderB.setName( "B" );
		leaderB.setFollower( follower );

		inTransaction( session -> {
			session.persist( leaderA );
			session.persist( leaderB );
		} );

		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Leader leaderReloaded = session.load( Leader.class, 2 );
			try {
				session.delete( leaderReloaded );
				transaction.commit();
				fail("Exception expected here! On fflush!");
			} catch (PersistenceException ex) {
				if ( transaction != null && TransactionStatus.ACTIVE.equals( transaction.getStatus() ) ) {
					transaction.rollback();
				}
			}
		}

		inTransaction( session -> {
			Leader leaderReloaded = session.load( Leader.class, 2 );
			leaderReloaded.setFollower( null );
			session.delete( leaderReloaded );
		} );

		inTransaction( session -> {
			Leader leaderReloaded = session.load( Leader.class, 3 );
			session.delete( leaderReloaded );
		} );

		inTransaction( session -> {
			List leaders = session.createQuery( "from Leader" ).list();
			List followers = session.createQuery( "from Follower" ).list();

			assertThat( leaders ).isEmpty();
			assertThat( followers ).isEmpty();
		} );
	}
}
