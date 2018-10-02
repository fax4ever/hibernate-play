package it.redhat.demo.test;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.Test;

public class InheritanceMappingTest {

	@Test
	public void testSingleTableInheritance() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( it.redhat.demo.entity.singletable.Node.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.singletable.NodeLink.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.singletable.SimpleNode.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.singletable.TextNode.class );
		try (SessionFactory factory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() ) ) {
			assertNotNull( factory );
		}
	}

	@Test
	public void testTablePerClassInheritance() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( it.redhat.demo.entity.tableperclass.Node.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.tableperclass.NodeLink.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.tableperclass.SimpleNode.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.tableperclass.TextNode.class );
		try (SessionFactory factory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() ) ) {
			assertNotNull( factory );
		}
	}

	@Test
	public void altTablePerClassInheritance() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( it.redhat.demo.entity.altperclass.Node.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.altperclass.NodeLink.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.altperclass.SimpleNode.class );
		configuration.addAnnotatedClass( it.redhat.demo.entity.altperclass.TextNode.class );
		try (SessionFactory factory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() ) ) {
			assertNotNull( factory );
		}
	}
}
