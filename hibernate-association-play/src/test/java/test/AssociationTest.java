package test;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import org.junit.Test;

import it.redhat.demo.entity.Leader;
import it.redhat.demo.entity.Follower;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class AssociationTest extends BaseSessionTest {

	public static final Integer LEADER_ID = 1;
	public static final Integer FOLLOWER_ID = 1;

	@Test
	public void test() {
		Throwable cause = null;

		inTransaction( session -> {
			Leader leader = new Leader( LEADER_ID, "Leader 1" );
			session.save( leader );

			Follower follower = new Follower( FOLLOWER_ID, "Follower" );
			follower.setLeader( leader );
			leader.getFollowers().add( follower );
			session.save( follower );
		} );

		try (Session session = sessionFactory.openSession()) {
			Transaction trx = session.beginTransaction();

			Leader leader = session.load( Leader.class, LEADER_ID );
			session.remove( leader );

			trx.commit();
			fail("PersistenceException is expected here!");

		} catch (PersistenceException pe) {
			cause = pe.getCause();
		}

		assertThat( cause ).isNotNull();
		assertThat( cause ).isInstanceOf( ConstraintViolationException.class );
	}
}
