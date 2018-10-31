package it.redhat.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.redhat.demo.orderby.Landmark;
import it.redhat.demo.orderby.StatePark;
import it.redhat.demo.orderby.Visitor;

public class OrderByTest {

	private SessionFactory factory;

	@Before
	public void setUp() {
		Configuration config = new Configuration()
				.addAnnotatedClass( Visitor.class ).addAnnotatedClass( Landmark.class ).addAnnotatedClass( StatePark.class );
		factory = config.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	}

	@After
	public void tearDown() {
		if ( factory == null ) {
			return;
		}
	}

	@Test
	public void test() {
		Landmark landmark = new Landmark( "Coliseum" );
		StatePark statePark = new StatePark( "Villa Borghese" );

		Visitor steve = new Visitor( "steve" );
		Visitor fabio = new Visitor( "fabio" );
		Visitor will = new Visitor( "will" );
		Visitor davide = new Visitor( "davide" );
		Visitor sanne = new Visitor( "sanne" );
		Visitor[] visitors = { steve, fabio, will, davide, sanne };

		for ( int i = 0; i < visitors.length; i++ ) {
			landmark.visit( visitors[i] );
			statePark.visit( visitors[i] );
		}

		try ( Session session = factory.openSession() ) {
			Transaction transaction = session.beginTransaction();
			for ( int i = 0; i < visitors.length; i++ ) {
				session.persist( visitors[i] );
			}
			session.persist( landmark );
			session.persist( statePark );
			transaction.commit();
		}

		try ( Session session = factory.openSession() ) {
			Landmark landmarkLoaded = session.load( Landmark.class, landmark.getName() );
			StatePark stateParkLoaded = session.load( StatePark.class, statePark.getName() );
			assertThat( landmarkLoaded.getVisitors() ).containsExactly( davide, fabio, sanne, steve, will );
			assertThat( stateParkLoaded.getVisitors() ).containsExactly( steve, fabio, will, davide, sanne );
		}
	}
}
