package it.redhat.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ElementCollectionTest {

	public static final String NAME = "Office";
	public static final String ADDRESS = "via Andrea Doria, 41M 00192 Rome Italy";

	private SessionFactory factory;

	private Place person;

	@Before
	public void setUp() {
		person = new Place();
		person.setName( NAME );
		ArrayList<String> addresses = new ArrayList<>();
		addresses.add( ADDRESS );
		addresses.add( ADDRESS );
		addresses.add( ADDRESS );
		person.setAddresses( addresses );

		Configuration config = new Configuration().addAnnotatedClass( Place.class );

		factory = config.buildSessionFactory( new StandardServiceRegistryBuilder().build() );

	}

	@After
	public void tearDown() {
		if ( factory == null ) {
			return;
		}

		try ( Session session = factory.openSession() ) {
			Transaction transaction = session.beginTransaction();
			session.remove( person );
			transaction.commit();
		}
		finally {
			factory.close();
		}
	}

	@Test
	public void test() {
		try ( Session session = factory.openSession() ) {
			Transaction transaction = session.beginTransaction();
			session.persist( person );
			transaction.commit();
		}

		try ( Session session = factory.openSession() ) {
			Place load = session.load( Place.class, NAME );
			assertThat( load.getAddresses() ).containsExactly( ADDRESS, ADDRESS, ADDRESS );
		}
	}
}
