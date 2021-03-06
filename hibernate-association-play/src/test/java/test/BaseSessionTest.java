package test;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import it.redhat.demo.entity.Leader;
import it.redhat.demo.entity.Message;
import it.redhat.demo.entity.Follower;
import it.redhat.demo.entity.Tag;

/**
 * @author Fabio Massimo Ercoli
 */
public abstract class BaseSessionTest {

	protected static SessionFactory sessionFactory;

	@BeforeClass
	public static void beforeClass() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Leader.class );
		configuration.addAnnotatedClass( Follower.class );
		configuration.addAnnotatedClass( Tag.class );
		configuration.addAnnotatedClass( Message.class );
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
				if ( transaction != null && transaction.isActive() ) {
					transaction.rollback();
				}
				throw throwable;
			}

			transaction.commit();
			session.clear();
		}
	}
}
