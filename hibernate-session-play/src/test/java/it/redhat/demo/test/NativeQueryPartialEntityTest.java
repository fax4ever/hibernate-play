package it.redhat.demo.test;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import javax.persistence.PersistenceException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import it.redhat.demo.entity.Party;

/**
 * @author Fabio Massimo Ercoli
 */
public class NativeQueryPartialEntityTest extends BaseSessionTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void test_nativeQuery_fullEntity() {
		inTransaction( session ->
			session.persist( new Party( 1, "Siamo fantastici", LocalDateTime.of( 2018, 10, 21, 22, 30, 0 ), "Rome" ) )
		);

		inTransaction( session -> {
			Party coolParty = (Party) session.createNativeQuery( "select * from Party where location = 'Rome'" )
				.addEntity( Party.class )
				.uniqueResult();

			assertThat( coolParty.getId() ).isEqualTo( 1 );
			assertThat( coolParty.getName() ).isEqualTo( "Siamo fantastici" );
			assertThat( coolParty.getMoment() ).isEqualTo( LocalDateTime.of( 2018, 10, 21, 22, 30, 0 ) );
			assertThat( coolParty.getLocation() ).isEqualTo( "Rome" );
		} );
	}

	@Test
	public void test_nativeQuery_partialEntity() {

		thrown.expect( PersistenceException.class );
		thrown.expectMessage( "org.hibernate.exception.SQLGrammarException: could not execute query" );

		inTransaction( session ->
		   session.persist( new Party( 2, "We're Cool", LocalDateTime.of( 2018, 11, 21, 22, 30, 0 ), "London" ) )
		);

		inTransaction( session -> {
			session.createNativeQuery( "select id, name from Party where location = 'London'" )
				.addEntity( Party.class )
				.uniqueResult();

			fail( "Expected exception before this!" );
		} );
	}

	@Test
	public void test_nativeQuery_projection_rowType() {
		inTransaction( session ->
		   session.persist( new Party( 3, "On est cool", LocalDateTime.of( 2018, 12, 21, 22, 30, 0 ), "Lyon" ) )
		);

		inTransaction( session -> {
			Object result = session.createNativeQuery( "select id, name from Party where location = 'Lyon'" )
				.uniqueResult();

			assertThat( result ).isEqualTo( new Object[] { 3, "On est cool" } );
		} );
	}

}
