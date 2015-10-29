package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class Controller {

	int numRequest = 0;
	
    @RequestMapping("/")
    public String index() {
    	System.out.print("hello called:" + (++numRequest));
    	if(numRequest > 10){
    		System.out.println(": with delay 3 sec");
    		giveDelay(3);
    	}else{
    		System.out.println(": no delay");
    	}
        return "Greetings from Spring Boot!";
    }
    

    @RequestMapping("/reset")
    public void resetCount() {
    	System.out.println("Number of count reset !");
    	this.numRequest=0;
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