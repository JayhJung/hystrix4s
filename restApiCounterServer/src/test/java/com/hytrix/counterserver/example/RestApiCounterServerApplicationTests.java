package com.hytrix.counterserver.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hytrix.counterserver.example.RestApiCounterServerApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApiCounterServerApplication.class)
@WebAppConfiguration
public class RestApiCounterServerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
