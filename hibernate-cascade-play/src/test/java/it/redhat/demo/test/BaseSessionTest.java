package it.redhat.demo.test;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import it.redhat.demo.entity.Master;
import it.redhat.demo.entity.Servant;

/**
 * @author Fabio Massimo Ercoli
 */
public abstract class BaseSessionTest {

	protected static SessionFactory sessionFactory;

	@BeforeClass
	public static void beforeClass() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Master.class );
		configuration.addAnnotatedClass( Servant.class );
		sessionFactory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	}

	@AfterClass
	public static void afterClass() {
		sessionFactory.close();
	}

	protected void inTransaction(Consumer<Session> consumer) {
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();

			try {
				consumer.accept( session );
			}
			catch (Throwable throwable) {
				if ( transaction != null && TransactionStatus.ACTIVE.equals( transaction.getStatus() ) ) {
					transaction.rollback();
				}
				throw throwable;
			}

			transaction.commit();
			session.clear();
		}
	}
}
