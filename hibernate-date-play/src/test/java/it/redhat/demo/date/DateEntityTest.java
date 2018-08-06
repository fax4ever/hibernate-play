package it.redhat.demo.date;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DateEntityTest extends BaseSessionTest {

	@Test
	public void testUtilDates() {
		LocalDateEntity entity = new LocalDateEntity( 1, "A entity using Java8 local date" );

		inTransaction( session -> {
			session.persist( entity );
		} );

		inTransaction( session -> {
			LocalDateEntity reloaded = session.load( LocalDateEntity.class, 1 );
			assertThat( reloaded.getMoment() ).isNotNull();
		} );
	}


	@Test
	public void testLocalDates() {
		UtilDateEntity entity = new UtilDateEntity( 1, "A entity using classic Java util.Date" );

		inTransaction( session -> {
			session.persist( entity );
		} );

		inTransaction( session -> {
			UtilDateEntity reloaded = session.load( UtilDateEntity.class, 1 );
			assertThat( reloaded.getMoment() ).isNotNull();
		} );
	}

}
