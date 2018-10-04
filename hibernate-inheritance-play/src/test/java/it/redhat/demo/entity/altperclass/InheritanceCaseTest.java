package it.redhat.demo.entity.altperclass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InheritanceCaseTest {

	private SessionFactory factory;

	private Node parent;
	private Node simpleChild;
	private TextNode textChild;
	private NodeLink linkToSimple;
	private NodeLink linkToText;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		parent = new SimpleNode( 7, "root" );
		simpleChild = new SimpleNode( 1, "child 1" );
		textChild = new TextNode( 2, "child 2", "a text" );

		linkToSimple = new NodeLink( 3 );
		linkToSimple.assignSource( parent );
		linkToSimple.assignTarget( simpleChild );

		linkToText = new NodeLink( 9 );
		linkToText.assignSource( parent );
		linkToText.assignTarget( textChild );
	}

	@After
	public void tearDown() {
		if ( factory == null ) {
			return;
		}

		try ( Session session = factory.openSession() ) {
			session.delete( parent );
			session.delete( simpleChild );
			session.delete( textChild );
			session.delete( linkToText );
			session.delete( linkToSimple );
		}
		finally {
			factory.close();
		}
	}

	@Test
	public void testPolymorphicList() {

		// why this exception?
		thrown.expect( ObjectNotFoundException.class );
		thrown.expectMessage( "No row with the given identifier exists: [it.redhat.demo.entity.altperclass.SimpleNode#7]" );

		Configuration config = new Configuration()
				.addAnnotatedClass( Node.class )
				.addAnnotatedClass( SimpleNode.class )
				.addAnnotatedClass( TextNode.class )
				.addAnnotatedClass( NodeLink.class );

		factory = config.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
		try ( Session session = factory.openSession() ) {
			session.save( parent );
			session.save( simpleChild );
			session.save( textChild );
			session.save( linkToText );
			session.save( linkToSimple );
		}

		try ( Session session = factory.openSession() ) {
			SimpleNode parentReloaded = session.load( SimpleNode.class, parent.id );

			// it fails here: on getChildren on Hibernate proxy
			assertThat( parentReloaded.getChildren() ).containsExactly( linkToSimple, linkToText );
			fail( "expecting the error just before this loc" );
		}
	}
}
