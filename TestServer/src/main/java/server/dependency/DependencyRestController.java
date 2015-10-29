package server.dependency;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class DependencyRestController {

	int numRequest = 0;
	
	boolean latency = false;
	
    @RequestMapping("/")
    public String index() {
    	System.out.println("hello called:" + (++numRequest));
    	if(numRequest > 300 && latency == false){
    		latency = true;
    		System.out.println(": with delay 5 sec");
    	}
    	
    	if(latency) {
    		giveDelay(5);    
    		
    	}else{
    		System.out.println(": no delay");
    	}
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/healthcheck")
    public String healthcheckWithLatency() {
    	System.out.println("health check with latency called !!");
    	
    	if(latency) {
    		giveDelay(5);    
    		
    	}else{
    		System.out.println(": health check with no delay");
    	}
    	
        return "Health Check!!";
    }
    
    @RequestMapping("/health")
    public String healthcheck() {
    	System.out.println("health check called !!");
    	
        return "Health Check!!";
    }
    
    @RequestMapping("/reset")
    public String reset() {
    	System.out.println("reset called!!");
    	numRequest = 0;
        return "reset";
    }
    
	private void giveDelay(int sec){
		
		try {
			for(int i = 0; i < sec ; i ++){
				Thread.sleep(1000);
				System.out.print(".");
			}
			System.out.println(sec + " sec has elapsed");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}