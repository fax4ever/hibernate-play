/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package fax.open.demo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TypeOperatorTest {

	private SessionFactory sessionFactory;

	@Before
	public void before() {
		initSessionFactory();
		initData();
	}

	@After
	public void after() {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@Test
	public void baseClassOnlyInstances() {
		try ( Session session = sessionFactory.openSession() ) {
			// we want all animal that are not dogs
			Set<Class<Animal>> types = Collections.singleton( Animal.class );

			Query<Animal> query = session.createQuery(
					"select e from Animal e where type(e) in (:types)", Animal.class );
			query.setParameterList( "types", types );

			List<Animal> animals = query.getResultList();
			assertThat( animals ).extracting( "name" ).containsExactly( "Fuffy" );

			Query<Long> count = session.createQuery(
					"select count(e) from Animal e where type(e) in (:types)", Long.class );
			count.setParameterList( "types", types );

			List<Long> counts = count.getResultList();
			assertThat( counts ).containsExactly( new Long[] { 1L } );
		}
	}

	@Test
	@Ignore // verify why this doesn't work
	public void noHierarchy() {
		try ( Session session = sessionFactory.openSession() ) {
			Set<Class<Box>> types = Collections.singleton( Box.class );

			Query<Box> query = session.createQuery(
					"select e from Box e where type(e) in (:types)", Box.class );
			query.setParameterList( "types", types );

			List<Box> boxes = query.getResultList();
			assertThat( boxes ).isEmpty();
		}
	}

	private void initSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Animal.class );
		configuration.addAnnotatedClass( Dog.class );
		configuration.addAnnotatedClass( Box.class );
		sessionFactory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
	}

	private void initData() {
		try ( Session session = sessionFactory.openSession() ) {
			Transaction trx = session.beginTransaction();

			Animal fuffy = new Animal();
			fuffy.setId( 1 );
			fuffy.setName( "Fuffy" );
			session.persist( fuffy );

			Dog foffy = new Dog();
			foffy.setId( 2 );
			foffy.setBones( 3 );
			foffy.setName( "Foffy" );
			session.persist( foffy );

			trx.commit();
		}
	}
}
