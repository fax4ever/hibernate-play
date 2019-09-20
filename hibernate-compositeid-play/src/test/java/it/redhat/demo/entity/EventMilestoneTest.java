package it.redhat.demo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventMilestoneTest {

	private SessionFactory factory;

	@Test
	public void test() {
		try ( Session session = factory.openSession() ) {
			EventMilestone entity = new EventMilestone();
			entity.setId( new EventMilestonePK( 739L ) );

			session.persist( entity );

			assertThat( entity.getId().getEventId() ).isNotNull();
			assertThat( entity.getId().getEventMilestoneId() ).isNotNull();
		}
	}

	@Before
	public void before() {
		Configuration config = new Configuration().addAnnotatedClass( EventMilestone.class );
		factory = config.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	}

	@After
	public void after() {
		if ( factory != null ) {
			factory.close();
		}
	}
}
