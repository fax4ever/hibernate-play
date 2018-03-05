import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import it.redhat.demo.entity.Party;

/**
 * @author Fabio Massimo Ercoli
 */
public class PersistenceTest {

	private static SessionFactory sessionFactory;

	@BeforeClass
	public static void beforeClass() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Party.class );
		sessionFactory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	}

	@AfterClass
	public static void afterClass() {
		sessionFactory.close();
	}

	@Test
	public void test_nativeQuery_fullEntity() {
		inTransaction( session ->
			session.persist( new Party( 1, "Cool", LocalDateTime.now(), "Rome" ) )
		);

		inTransaction( session -> {
			Party coolParty = (Party) session.createNativeQuery( "select * from Party where location = 'Rome'" )
				.addEntity( Party.class )
				.uniqueResult();

			assertNotNull( coolParty );
		} );
	}

	@Test(expected = PersistenceException.class)
	public void test_nativeQuery_partialEntity() {
		inTransaction( session ->
		   session.persist( new Party( 2, "Cool II", LocalDateTime.now(), "London" ) )
		);

		inTransaction( session -> {
			session.createNativeQuery( "select id, name from Party where location = 'London'" ).addEntity( Party.class )
				.uniqueResult();

			fail( "Expected exception before this!" );
		} );
	}

	private void inTransaction(Consumer<Session> consumer) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			try {
				consumer.accept( session );
			}
			catch (Throwable throwable) {
				if ( transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE ) {
					transaction.rollback();
				}
				throw throwable;
			}

			transaction.commit();
			session.clear();
		}
	}

}
