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

	@Test
	public void testCriteria_stringLiteral_strategyAuto() {
		EntityManager em = autoFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Person> cq = cb.createQuery( Person.class );
			Root<Person> person = cq.from( Person.class );

			cq.select( person )
					.where( cb.equal( person.get( "name" ), "Davide" ) );

			List<Person> people = em.createQuery( cq ).getResultList();
			assertThat( people ).hasSize( 2 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	@Test
	public void testCriteria_numberLiteral_strategyAuto() {
		EntityManager em = autoFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Person> cq = cb.createQuery( Person.class );
			Root<Person> person = cq.from( Person.class );

			cq.select( person )
					.where( cb.equal( person.get( "petsOwned" ), 5 ) );

			List<Person> people = em.createQuery( cq ).getResultList();
			assertThat( people ).hasSize( 2 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	@Test
	public void testCriteria_stringLiteral_strategyBind() {
		EntityManager em = bindFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Person> cq = cb.createQuery( Person.class );
			Root<Person> person = cq.from( Person.class );

			cq.select( person )
					.where( cb.equal( person.get( "name" ), "Davide" ) );

			List<Person> people = em.createQuery( cq ).getResultList();
			assertThat( people ).hasSize( 2 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	@Test
	public void testCriteria_numberLiteral_strategyBind() {
		EntityManager em = bindFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Person> cq = cb.createQuery( Person.class );
			Root<Person> person = cq.from( Person.class );

			cq.select( person )
					.where( cb.equal( person.get( "petsOwned" ), 5 ) );

			List<Person> people = em.createQuery( cq ).getResultList();
			assertThat( people ).hasSize( 2 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
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
					cb.sum( cb.<Long>selectCase()
							.when( documentHavingAPersonWithID, cb.literal( 1L ) )
							.otherwise( cb.literal( 0L ) )
					).as( Long.class )
			)
					.groupBy( document.get( "id" ) )
					.orderBy( cb.asc( document.get( "id" ) ) );

			List<Tuple> tuples = em.createQuery( query ).getResultList();
			assertThat( tuples ).hasSize( 3 );
			assertThat( tuples.get( 0 ).get( 0 ) ).isEqualTo( 3 );
			assertThat( tuples.get( 0 ).get( 1 ) ).isEqualTo( 3l );
			assertThat( tuples.get( 1 ).get( 0 ) ).isEqualTo( 7 );
			assertThat( tuples.get( 1 ).get( 1 ) ).isEqualTo( 2l );
			assertThat( tuples.get( 2 ).get( 0 ) ).isEqualTo( 9 );
			assertThat( tuples.get( 2 ).get( 1 ) ).isEqualTo( 0l );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	protected static EntityManagerFactory initEmf(String unit) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory( unit );
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();

			Person p1 = new Person( "Davide", 3 );
			Person p2 = new Person( "Sanne", 5 );
			Document d1 = new Document( 7 );
			d1.associate( p1, p2 );

			Person p3 = new Person( "Guillaume", 3 );
			Person p4 = new Person( "Davide", 1 );
			Person p5 = new Person( "Yoann", 5 );
			Document d2 = new Document( 3 );
			d2.associate( p3, p4, p5 );

			Document d3 = new Document( 9 );

			em.persist( p1 );
			em.persist( p2 );
			em.persist( p3 );
			em.persist( p4 );
			em.persist( p5 );
			em.persist( d1 );
			em.persist( d2 );
			em.persist( d3 );
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
