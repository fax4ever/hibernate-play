package it.redhat.demo.test;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.junit.Test;

import it.redhat.demo.entity.Condominium;
import it.redhat.demo.entity.CondominiumBuilding;
import it.redhat.demo.entity.EntityA;
import it.redhat.demo.entity.EntityB;
import it.redhat.demo.entity.Floor;
import it.redhat.demo.entity.Simple;
import it.redhat.demo.entity.Tower;
import it.redhat.demo.entity.TreeNode;

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

	@Test
	public void test_buildSessionFactory_treeCompositeEntity() {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass( TreeNode.class );

		SessionFactory sessionFactory = configuration.buildSessionFactory();
		assertNotNull( sessionFactory );
	}

	@Test
	public void test_buildSessionFactory_multiCollections() {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass( Tower.class );
		configuration.addAnnotatedClass( Floor.class );
		configuration.addAnnotatedClass( CondominiumBuilding.class );
		configuration.addAnnotatedClass( Condominium.class );

		SessionFactory sessionFactory = configuration.buildSessionFactory();
		assertNotNull( sessionFactory );
	}
}
