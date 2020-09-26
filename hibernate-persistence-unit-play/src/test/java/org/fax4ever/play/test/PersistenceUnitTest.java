package org.fax4ever.play.test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import org.assertj.core.api.Assertions;

public class PersistenceUnitTest {

	@Test
	public void test() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory( "primary_pu" );
		Assertions.assertThat(emf).isNotNull();
	}

}
