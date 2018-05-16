package it.redhat.demo.test;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.junit.Test;

import it.redhat.demo.entity.EntityA;
import it.redhat.demo.entity.EntityB;
import it.redhat.demo.entity.Simple;

public class SessionFactoryBuildTest {

	@Test
	public void test_buildSessionFactory_SingleEntity() {

		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Simple.class );
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		assertNotNull( sessionFactory );
	}

	@Test
	public void test_buildSessionFactory_OnetoOne() {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass( EntityA.class );
		configuration.addAnnotatedClass( EntityB.class );

		SessionFactory sessionFactory = configuration.buildSessionFactory();
		assertNotNull( sessionFactory );
	}

}
