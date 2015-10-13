package server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rest4s.config.annotation.Rest4sConf;
import rest4s.restclient.Rest4s;
import rest4s.restclient.Rest4sCall;


@RestController
public class TargetCallerRestController {

	private String defaultUrl = "http://localhost:8080/";
	private String healthCheckUrl = "http://localhost:8080/healthCheck";

	@RequestMapping("/")
	public String index() {
		System.out.print("dummy called:");
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(value = "http", method = RequestMethod.GET)
	public String requestWithHttpClientGet() throws Exception {
		System.out.println("http Get reqeust called!");
		return new Rest4sCall().getRequest(defaultUrl);

	}

	@RequestMapping(value = "http", method = RequestMethod.POST)
	public String requestWithHttpClientPost() throws Exception {
		System.out.println("http POST reqeust called!");
		return new Rest4sCall().postRequest(defaultUrl);
	}
	
	
	@RequestMapping(value = "hystrix1", method=RequestMethod.GET)
	@Rest4sConf(fallback="getReqFallBack", timeoutMilliSec=1000, healthCheckUrl="/")
	public String reqeustWithHystrixClientGet1() throws Exception{
		System.out.println("hystrix GET reqeust called!");
		return Rest4s.simpleGet(defaultUrl);
	}
	
	@RequestMapping(value = "hystrix2", method=RequestMethod.GET)
	@Rest4sConf(fallback="getReqFallBack", timeoutMilliSec=2000)
	public String reqeustWithHystrixClientGet2() throws Exception{
		System.out.println("hystrix GET reqeust called!");
		return Rest4s.simpleGet(defaultUrl);
	}
	
	@RequestMapping(value = "hystrix3", method=RequestMethod.GET)
	@Rest4sConf(fallback="getReqFallBack", timeoutMilliSec=3000)
	public String reqeustWithHystrixClientGet3() throws Exception{
		System.out.println("hystrix GET reqeust called!");
		return Rest4s.simpleGet(defaultUrl);
	}
	@RequestMapping(value = "hystrix4", method=RequestMethod.GET)
	@Rest4sConf(fallback="getReqFallBack", timeoutMilliSec=4000)
	public String reqeustWithHystrixClientGet4() throws Exception{
		System.out.println("hystrix GET reqeust called!");
		return Rest4s.simpleGet(defaultUrl);
	}
	
	@RequestMapping(value = "hystrix5", method=RequestMethod.GET)
	@Rest4sConf(fallback="getReqFallBack", timeoutMilliSec=5000)
	public String reqeustWithHystrixClientGet5() throws Exception{
		System.out.println("hystrix GET reqeust called!");
		return Rest4s.simpleGet(defaultUrl);
	}
	@RequestMapping(value = "hystrix6", method=RequestMethod.GET)
	@Rest4sConf(fallback="getReqFallBack", timeoutMilliSec=6000)
	public String reqeustWithHystrixClientGet6() throws Exception{
		System.out.println("hystrix GET reqeust called!");
		return Rest4s.simpleGet(defaultUrl);
	}
	
	public String getReqFallBack(){
		System.out.println("Annotated callback");
		return "annotated callback";
	}
	
	@RequestMapping(value = "hystrix", method=RequestMethod.POST)
	public String reqeustWithHystrixClientPost() throws Exception{
		System.out.println("hystrix POST reqeust called!");
		return Rest4s.simplePost(defaultUrl);
	}

}