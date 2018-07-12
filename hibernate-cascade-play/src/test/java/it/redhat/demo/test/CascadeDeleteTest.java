package it.redhat.demo.test;

import java.util.List;

import org.junit.Test;

import it.redhat.demo.entity.Master;
import it.redhat.demo.entity.Servant;

import static org.fest.assertions.Assertions.assertThat;

public class CascadeDeleteTest extends BaseSessionTest {

	@Test
	public void test() {
		final Servant servant = new Servant();
		servant.setId( 1 );
		servant.setName( "S" );

		final Master masterA = new Master();
		masterA.setId( 2 );
		masterA.setName( "A" );
		masterA.setServant( servant );

		final Master masterB = new Master();
		masterB.setId( 3 );
		masterB.setName( "B" );
		masterA.setServant( servant );

		inTransaction( session -> {
			session.persist( masterA );
			session.persist( masterB );
		} );

		inTransaction( session -> {
			Master masterReloaded = session.load( Master.class, 2 );
			session.delete( masterReloaded );
		} );

		inTransaction( session -> {
			Master masterReloaded = session.load( Master.class, 3 );

			// because it was removed by previous delete!
			assertThat( masterReloaded.getServant() ).isNull();

			session.delete( masterReloaded );
		} );

		inTransaction( session -> {
			List masters = session.createQuery( "from Master" ).list();
			List servants = session.createQuery( "from Servant" ).list();

			assertThat( masters ).isEmpty();
			assertThat( servants ).isEmpty();
		} );
	}
}
