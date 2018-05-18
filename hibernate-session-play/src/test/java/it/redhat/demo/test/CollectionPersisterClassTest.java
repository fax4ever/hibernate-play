package it.redhat.demo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.collection.BasicCollectionPersister;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.collection.OneToManyPersister;

import org.junit.Test;

import it.redhat.demo.entity.ManyToOneBi;
import it.redhat.demo.entity.NoRel;
import it.redhat.demo.entity.OneToManyBi;
import it.redhat.demo.entity.OneToManyJoin;
import it.redhat.demo.entity.OneToManyUni;
import it.redhat.demo.entity.OneToOneUni;

public class CollectionPersisterClassTest {

	@Test
	public void aloneEntity_noCollectionPersisters() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( NoRel.class );
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		assertNoCollectionPersisters( sessionFactory );
	}

	@Test
	public void oneToOne_noCollectionPersisters() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( OneToOneUni.class );
		configuration.addAnnotatedClass( NoRel.class );
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		assertNoCollectionPersisters( sessionFactory );
	}

	@Test
	public void oneToMany_basicCollectionPersister() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( NoRel.class );
		configuration.addAnnotatedClass( OneToManyUni.class );
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		assertOneCollectionPersister( sessionFactory, OneToManyUni.class, "messages", BasicCollectionPersister.class );
	}

	@Test
	public void oneToManyJoin_oneToManyPersister() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( NoRel.class );
		configuration.addAnnotatedClass( OneToManyJoin.class );
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		assertOneCollectionPersister( sessionFactory, OneToManyJoin.class, "messages", OneToManyPersister.class );
	}

	@Test
	public void oneToManyBidirectional_oneToManyPersister() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( ManyToOneBi.class );
		configuration.addAnnotatedClass( OneToManyBi.class );
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		assertOneCollectionPersister( sessionFactory, OneToManyBi.class, "messages", OneToManyPersister.class );
	}

	private void assertNoCollectionPersisters(SessionFactory sessionFactory) {
		assertNotNull( sessionFactory );
		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
		Map<String, CollectionPersister> collectionPersisters = sessionFactoryImpl.getMetamodel().collectionPersisters();
		assertNotNull( collectionPersisters );
		assertEquals( 0, collectionPersisters.size() );
	}

	private void assertOneCollectionPersister(SessionFactory sessionFactory, Class<?> entity, String field, Class<?> type) {
		assertNotNull( sessionFactory );

		Map<String, CollectionPersister> collectionPersisters = ( (SessionFactoryImpl) sessionFactory ).getMetamodel().collectionPersisters();
		assertNotNull( collectionPersisters );
		assertEquals( 1, collectionPersisters.size() );

		CollectionPersister collectionPersister = collectionPersisters.get( entity.getName() + "." + field );
		assertNotNull( collectionPersisters );
		assertEquals( type, collectionPersister.getClass() );
	}

}
