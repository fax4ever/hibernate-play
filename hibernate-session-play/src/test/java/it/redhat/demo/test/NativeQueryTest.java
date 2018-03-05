package it.redhat.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import javax.persistence.PersistenceException;

import org.junit.Test;

import it.redhat.demo.entity.Party;

/**
 * @author Fabio Massimo Ercoli
 */
public class NativeQueryTest extends BaseSessionTest {

	@Test
	public void test_nativeQuery_fullEntity() {
		inTransaction( session ->
			session.persist( new Party( 1, "Cool", LocalDateTime.now(), "Rome" ) )
		);

		inTransaction( session -> {
			Party coolParty = (Party) session.createNativeQuery( "select * from Party where location = 'Rome'" )
				.addEntity( Party.class )
				.uniqueResult();

			assertNotNull( coolParty );
		} );
	}

	@Test(expected = PersistenceException.class)
	public void test_nativeQuery_partialEntity() {
		inTransaction( session ->
		   session.persist( new Party( 2, "Cool II", LocalDateTime.now(), "London" ) )
		);

		inTransaction( session -> {
			session.createNativeQuery( "select id, name from Party where location = 'London'" ).addEntity( Party.class )
				.uniqueResult();

			fail( "Expected exception before this!" );
		} );
	}

}
