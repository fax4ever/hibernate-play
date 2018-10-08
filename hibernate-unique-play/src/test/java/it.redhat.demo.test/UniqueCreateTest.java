package it.redhat.demo.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.redhat.demo.entity.Client;
import it.redhat.demo.entity.User;

public class UniqueCreateTest {

	private static final String USER_NAME = "Am I?";
	private static final String CLIENT_NAME = "My name is";

	private SessionFactory factory;

	private Client client;
	private User user;

	@Before
	public void setUp() {
		client = new Client( 7, CLIENT_NAME );
		user = new User( 3, USER_NAME, 7l, 39l );
		user.setClient( client );

		Configuration config = new Configuration()
				.addAnnotatedClass( Client.class )
				.addAnnotatedClass( User.class );

		factory = config.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
		try ( Session session = factory.openSession() ) {
			Transaction transaction = session.beginTransaction();
			session.persist( client );
			session.persist( user );
			transaction.commit();
		}
	}

	@After
	public void tearDown() {
		if ( factory == null ) {
			return;
		}

		try ( Session session = factory.openSession() ) {
			Transaction transaction = session.beginTransaction();
			session.remove( user );
			session.remove( client );
			transaction.commit();
		}
		finally {
			factory.close();
		}
	}

	@Test
	public void test() {
		try ( Session session = factory.openSession() ) {
			List<User> userByClientName = session.createQuery( "select u from User u where u.client.name = :name" )
					.setParameter( "name", CLIENT_NAME )
					.getResultList();

			assertThat( userByClientName.get( 0 ).getName() ).isEqualTo( USER_NAME );
		}
	}
}
