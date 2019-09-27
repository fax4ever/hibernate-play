package it.redhat.demo.entity.alt;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.redhat.demo.entity.EventMilestone;

public class MyEntityTest {

	private SessionFactory factory;

	@Test
	public void test() {
		try ( Session session = factory.openSession() ) {
			MyEntity entity = new MyEntity();
			entity.setId( new MyEntityId( 739L ) );

			session.persist( entity );

			assertThat( entity.getId().getProvidedId() ).isNotNull();
			assertThat( entity.getId().getGeneratedId() ).isNotNull();
		}
	}

	@Before
	public void before() {
		Configuration config = new Configuration().addAnnotatedClass( MyEntity.class );
		factory = config.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	}

	@After
	public void after() {
		if ( factory != null ) {
			factory.close();
		}
	}
}
