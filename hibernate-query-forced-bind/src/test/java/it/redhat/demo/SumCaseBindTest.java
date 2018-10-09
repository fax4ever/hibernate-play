package it.redhat.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test taken from Hibernate issue:
 * https://hibernate.atlassian.net/browse/HHH-13001
 */
public class SumCaseBindTest {

	private static EntityManagerFactory autoFactory;
	private static EntityManagerFactory bindFactory;

	@BeforeClass
	public static void setUp() {
		autoFactory = initEmf( "literal-handling-auto" );
		bindFactory = initEmf( "literal-handling-bind" );
	}

	@Test
	public void testSum_literalHandlingAuto() {
		queryLiteralOn( autoFactory );
	}

	@Test(expected = NullPointerException.class)
	public void testSum_literalHandlingBind() {
		queryLiteralOn( bindFactory );
	}

	@AfterClass
	public static void tearDown() {
		if ( autoFactory != null ) {
			autoFactory.close();
		}
		if ( bindFactory != null ) {
			bindFactory.close();
		}
	}

	private void queryLiteralOn(EntityManagerFactory factory) {
		EntityManager em = factory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<Document> document = query.from( Document.class );
			Root<Person> person = query.from( Person.class );

			Predicate documentHavingAPersonWithID = cb.equal( document.join( "people", JoinType.LEFT ).get( "id" ), person.get( "id" ) );

			query.multiselect(
				document.get( "id" ),
				cb.sum(cb.<Long>selectCase()
					.when( documentHavingAPersonWithID, cb.literal( 1L ) )
					.otherwise( cb.literal( 0L ) )
				).as( Long.class )
			)
			.groupBy( document.get( "id" ) );

			List l = em.createQuery( query ).getResultList();
			assertThat( l ).hasSize( 1 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	private static EntityManagerFactory initEmf(String unit) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory( unit );
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();

			Person p1 = new Person(3);
			Person p2 = new Person(9);
			Document d = new Document(7);

			p1.getLocalized().put( 1, "p1.1" );
			p1.getLocalized().put( 2, "p1.2" );
			p2.getLocalized().put( 1, "p2.1" );
			p2.getLocalized().put( 2, "p2.2" );

			d.getPeople().put( 1, p1 );
			d.getPeople().put( 2, p2 );

			em.persist( p1 );
			em.persist( p2 );
			em.persist( d );
			tx.commit();
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}

		return emf;
	}
}
