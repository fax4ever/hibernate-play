package it.redhat.demo;

import static it.redhat.demo.SumCaseBindTest.initEmf;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CaseBindTest {

	private static EntityManagerFactory autoFactory;
	private static EntityManagerFactory bindFactory;

	@BeforeClass
	public static void setUp() {
		autoFactory = initEmf( "literal-handling-auto" );
		bindFactory = initEmf( "literal-handling-bind" );
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

	@Test
	public void queryCaseOnAutoLiteralHandling() {
		EntityManager em = autoFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<Person> person = query.from( Person.class );

			Predicate petOwnerLvl1 = cb.between( person.get( "petsOwned" ), 0, 1 );
			Predicate petOwnerLvl2 = cb.between( person.get( "petsOwned" ), 2, 3 );
			CriteriaBuilder.Case<Integer> selectCase = cb.selectCase();
			selectCase.when( petOwnerLvl1, 1 )
					.when( petOwnerLvl2, 2 )
					.otherwise( 3 );

			query.multiselect( person.get( "name" ), selectCase );

			List<Tuple> resultList = em.createQuery( query ).getResultList();
			assertThat( resultList ).isNotNull();
			assertThat( resultList ).hasSize( 5 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	@Test
	public void querySumCaseOnAutoLiteralHandling() {
		EntityManager em = autoFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<Person> person = query.from( Person.class );

			Predicate petOwnerLvl1 = cb.between( person.get( "petsOwned" ), 0, 1 );
			Predicate petOwnerLvl2 = cb.between( person.get( "petsOwned" ), 2, 3 );

			CriteriaBuilder.Case<Integer> selectCase = cb.selectCase();
			selectCase.when( petOwnerLvl1, 1 )
					.when( petOwnerLvl2, 2 )
					.otherwise( 3 );

			query.multiselect( person.get( "name" ), cb.sum( selectCase ) )
					.groupBy( person.get( "name" ) )
					.orderBy( cb.asc( person.get( "name" ) ) );

			List<Tuple> resultList = em.createQuery( query ).getResultList();
			assertThat( resultList ).isNotNull();
			assertThat( resultList ).hasSize( 4 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	@Test
	public void queryCaseOnBindLiteralHandling() {
		EntityManager em = bindFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<Person> person = query.from( Person.class );

			Predicate petOwnerLvl1 = cb.between( person.get( "petsOwned" ), 0, 1 );
			Predicate petOwnerLvl2 = cb.between( person.get( "petsOwned" ), 2, 3 );
			CriteriaBuilder.Case<Integer> selectCase = cb.selectCase();
			selectCase.when( petOwnerLvl1, 1 )
					.when( petOwnerLvl2, 2 )
					.otherwise( 3 );

			query.multiselect( person.get( "name" ), selectCase );

			List<Tuple> resultList = em.createQuery( query ).getResultList();
			assertThat( resultList ).isNotNull();
			assertThat( resultList ).hasSize( 5 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}

	@Test(expected = NullPointerException.class)
	public void querySumCaseOnBindLiteralHandling() {
		EntityManager em = bindFactory.createEntityManager();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = cb.createTupleQuery();
			Root<Person> person = query.from( Person.class );

			Predicate petOwnerLvl1 = cb.between( person.get( "petsOwned" ), 0, 1 );
			Predicate petOwnerLvl2 = cb.between( person.get( "petsOwned" ), 2, 3 );

			CriteriaBuilder.Case<Integer> selectCase = cb.selectCase();
			selectCase.when( petOwnerLvl1, 1 )
					.when( petOwnerLvl2, 2 )
					.otherwise( 3 );

			query.multiselect( person.get( "name" ), cb.sum( selectCase ) )
					.groupBy( person.get( "name" ) )
					.orderBy( cb.asc( person.get( "name" ) ) );

			List<Tuple> resultList = em.createQuery( query ).getResultList();
			assertThat( resultList ).isNotNull();
			assertThat( resultList ).hasSize( 4 );
		}
		finally {
			if ( em != null ) {
				em.close();
			}
		}
	}
}
