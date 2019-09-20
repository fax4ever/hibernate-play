package it.redhat.demo.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateTest {

	private SessionFactory factory;

	private MyEntity entity;

	@Before
	public void before() {
		Configuration config = new Configuration().addAnnotatedClass( MyEntity.class );
		factory = config.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	}

	@After
	public void after() {
		if ( factory != null ) {
			factory.close();;
		}
	}

	@Test
	public void test() {
		try ( Session session = factory.openSession() ) {

		}
	}
}
