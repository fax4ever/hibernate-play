package test;

import org.junit.After;
import org.junit.Test;

import it.redhat.demo.entity.Message;
import it.redhat.demo.entity.Tag;

public class MessageTagTest extends BaseSessionTest {

	private Tag helloTag;
	private Tag microserviceTag;
	private Message[] messages = new Message[3];

	@Test
	public void createMessagesWithTags() throws Exception {
		helloTag = new Tag( "Hello" );
		microserviceTag = new Tag( "Microservice" );

		messages[0] = new Message( "fabio", "Nice! I'm happy to be here. Thank you guys!" );
		messages[1] = new Message( "fabio", "Please use the v4 instead of v2!" );
		messages[2] = new Message( "john", "Hello Thorntail v4!" );

		messages[0].getTags().add( helloTag );
		messages[1].getTags().add( microserviceTag );
		messages[2].getTags().add( helloTag );
		messages[2].getTags().add( microserviceTag );

		inTransaction( session -> {
			session.persist( helloTag );
			session.persist( microserviceTag );
			for (int i=0; i<messages.length; i++) {
				session.persist( messages[i] );
			}
		} );
	}

	@After
	public void deleteMessages() throws Exception {
		inTransaction( session -> {
			for (int i=0; i<messages.length; i++) {
				session.delete( messages[i] );
			}
			session.remove( helloTag );
			session.remove( microserviceTag );
		} );
	}
}
