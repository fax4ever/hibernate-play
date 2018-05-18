package it.redhat.demo.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import it.redhat.demo.entity.Festival;
import it.redhat.demo.entity.Party;

public class CascadeTest extends BaseSessionTest {

	public static final String ARTIST_1 = "Arctic Monkeys";
	public static final String ARTIST_2 = "Liam Gallagher";
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void test_cascade_merge() {
		String festivalName = "Sziget 2018";
		inTransaction( session -> {
			Party party = new Party( 4, ARTIST_1, LocalDateTime.of( 2018, 12, 21, 22, 30, 0 ), "Budapest" );
			Festival festival = new Festival( festivalName );
			festival.add( party );
			session.persist( festival );
		} );

		inTransaction( session -> {
			Festival festival = session.load( Festival.class, festivalName );
			Party party = festival.getParties().get( 0 );

			assertEquals( ARTIST_1, party.getName() );
			party.setName( ARTIST_2 );
			session.merge( festival );
		} );

		inTransaction( session -> {
			Festival festival = session.load( Festival.class, festivalName );
			Party party = festival.getParties().get( 0 );
			assertEquals( ARTIST_2, party.getName() );
		} );
	}

	@Test
	public void test_cascade_refresh() {
		String festivalName = "Sziget 2019";
		inTransaction( session -> {
			Party party = new Party( 5, ARTIST_1, LocalDateTime.of( 2019, 12, 21, 22, 30, 0 ), "Budapest" );
			Festival festival = new Festival( festivalName );
			festival.add( party );
			session.persist( festival );
		} );

		inTransaction( session -> {
			Festival festival = session.load( Festival.class, festivalName );
			Party party = festival.getParties().get( 0 );

			assertEquals( ARTIST_1, party.getName() );
			party.setName( ARTIST_2 );
			session.refresh( festival );
		} );

		inTransaction( session -> {
			Festival festival = session.load( Festival.class, festivalName );
			Party party = festival.getParties().get( 0 );
			assertEquals( ARTIST_1, party.getName() );
		} );
	}

}
