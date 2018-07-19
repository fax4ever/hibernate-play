package test;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import org.junit.Test;

import it.redhat.demo.entity.Master;
import it.redhat.demo.entity.Servant;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class AssociationTest extends BaseSessionTest {

	public static final Integer MASTER_ID = 1;
	public static final Integer SERVANT_ID = 1;

	@Test
	public void test() {
		Throwable cause = null;

		inTransaction( session -> {
			Master master = new Master( MASTER_ID, "Master 1" );
			session.save( master );

			Servant servant = new Servant( SERVANT_ID, "Servant" );
			servant.setMaster( master );
			master.getServants().add( servant );
			session.save( servant );
		} );

		try (Session session = sessionFactory.openSession()) {
			Transaction trx = session.beginTransaction();

			Master master = session.load( Master.class, MASTER_ID );
			session.remove( master );

			trx.commit();
			fail("PersistenceException is expected here!");

		} catch (PersistenceException pe) {
			cause = pe.getCause();
		}

		assertThat( cause ).isNotNull();
		assertThat( cause ).isInstanceOf( ConstraintViolationException.class );
	}
}
