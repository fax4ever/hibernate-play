package it.redhat.demo.test;

import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import org.junit.Test;

import it.redhat.demo.entity.Master;
import it.redhat.demo.entity.Servant;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

public class CascadeDeleteTest extends BaseSessionTest {

	@Test
	public void test() {
		final Servant servant = new Servant();
		servant.setId( 1 );
		servant.setName( "S" );

		final Master masterA = new Master();
		masterA.setId( 2 );
		masterA.setName( "A" );
		masterA.setServant( servant );

		final Master masterB = new Master();
		masterB.setId( 3 );
		masterB.setName( "B" );
		masterB.setServant( servant );

		inTransaction( session -> {
			session.persist( masterA );
			session.persist( masterB );
		} );

		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			Master masterReloaded = session.load( Master.class, 2 );
			try {
				session.delete( masterReloaded );
				transaction.commit();
				fail("Exception expected here! On fflush!");
			} catch (PersistenceException ex) {
				if ( transaction != null && TransactionStatus.ACTIVE.equals( transaction.getStatus() ) ) {
					transaction.rollback();
				}
			}
		}

		inTransaction( session -> {
			Master masterReloaded = session.load( Master.class, 2 );
			masterReloaded.setServant( null );
			session.delete( masterReloaded );
		} );

		inTransaction( session -> {
			Master masterReloaded = session.load( Master.class, 3 );
			session.delete( masterReloaded );
		} );

		inTransaction( session -> {
			List masters = session.createQuery( "from Master" ).list();
			List servants = session.createQuery( "from Servant" ).list();

			assertThat( masters ).isEmpty();
			assertThat( servants ).isEmpty();
		} );
	}
}
