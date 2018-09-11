package com.sigif.app;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SigifServicesTestConfig.class)
public class ApplicationContextProviderTest {

	@Test
	public void test() {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		assertNotNull(applicationContext);
	}

}
