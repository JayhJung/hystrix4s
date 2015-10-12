package server.client;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RequestServer {

	private static final Logger logger = LoggerFactory.getLogger(RequestServer.class);
	
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(RequestServer.class, args);

        logger.debug("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            logger.debug(beanName);
        }
    }

}