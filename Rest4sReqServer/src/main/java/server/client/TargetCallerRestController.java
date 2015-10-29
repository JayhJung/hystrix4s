package server.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rest4s.config.annotation.Rest4s;
import rest4s.restclient.RequestSettings;
import rest4s.restclient.Rest4sCall;
import rest4s.restclient.Rest4sClient;


@RestController
public class TargetCallerRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TargetCallerRestController.class);

	private String defaultUrl = "http://localhost:8080/";
	private String healthCheckUrl = "http://localhost:8080/healthCheck";

	@RequestMapping("/")
	public String index() {
		logger.debug("dummy called:");
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(value = "http", method = RequestMethod.GET)
	public String requestWithHttpClientGet() throws Exception {
		logger.debug("http Get reqeust called!");
		return new Rest4sCall().getRequest(defaultUrl);

	}

	@RequestMapping(value = "http", method = RequestMethod.POST)
	public String requestWithHttpClientPost() throws Exception {
		logger.debug("http POST reqeust called!");
		return new Rest4sCall().postRequest(defaultUrl);
	}
	
	
	@RequestMapping(value = "hystrix1", method=RequestMethod.GET)
	@Rest4s(fallback="getReqFallBack", timeoutMilliSec=1000, healthCheckUrl="/healthcheck")
	public String reqeustWithHystrixClientGet1() throws Exception{
		logger.debug("hystrix GET reqeust called!");
		return Rest4sClient.simpleGet(defaultUrl);
	}
	
	@RequestMapping(value = "hystrix2", method=RequestMethod.GET)
	@Rest4s(fallback="getReqFallBack", timeoutMilliSec=2000)
	public String reqeustWithHystrixClientGet2() throws Exception{
		logger.debug("hystrix GET reqeust called!");
		
/*		RequestSettings requestSettings = new RequestSettings("http://sds.samsung.com/")
		.httpRequestMethod("post")
		.header("headerkey1", "headervalue1")
		.header("headerkey2", "headervalue2")
		.cookie("cookiekey1", "cookievalue1")
		.cookie("cookiekey2", "cookievalue2")
		.queryParameterString("?param1=value1");
		
		Rest4sClient.callRequest(requestSettings);*/
		
		return Rest4sClient.simpleGet(defaultUrl);
	}
	
	@RequestMapping(value = "hystrix3", method=RequestMethod.GET)
	@Rest4s(fallback="getReqFallBack", timeoutMilliSec=3000)
	public String reqeustWithHystrixClientGet3() throws Exception{
		logger.debug("hystrix GET reqeust called!");
		return Rest4sClient.simpleGet(defaultUrl);
	}
	@RequestMapping(value = "hystrix4", method=RequestMethod.GET)
	@Rest4s(fallback="getReqFallBack", timeoutMilliSec=4000)
	public String reqeustWithHystrixClientGet4() throws Exception{
		logger.debug("hystrix GET reqeust called!");
		return Rest4sClient.simpleGet(defaultUrl);
	}
	
	@RequestMapping(value = "hystrix5", method=RequestMethod.GET)
	@Rest4s(fallback="getReqFallBack", timeoutMilliSec=5000)
	public String reqeustWithHystrixClientGet5() throws Exception{
		logger.debug("hystrix GET reqeust called!");
		return Rest4sClient.simpleGet(defaultUrl);
	}
	@RequestMapping(value = "hystrix6", method=RequestMethod.GET)
	@Rest4s(fallback="getReqFallBack", timeoutMilliSec=6000)
	public String reqeustWithHystrixClientGet6() throws Exception{
		logger.debug("hystrix GET reqeust called!");
		return Rest4sClient.simpleGet(defaultUrl);
	}
	
	public String getReqFallBack(){
		logger.debug("Annotated callback");
		return "annotated callback";
	}
	
	@RequestMapping(value = "hystrix", method=RequestMethod.POST)
	public String reqeustWithHystrixClientPost() throws Exception{
		logger.debug("hystrix POST reqeust called!");
		return Rest4sClient.simplePost(defaultUrl);
	}

}