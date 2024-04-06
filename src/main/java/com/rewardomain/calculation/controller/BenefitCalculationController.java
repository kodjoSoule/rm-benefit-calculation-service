package com.rewardomain.calculation.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rewardomain.calculation.BenefitRestaurant;
import com.rewardomain.calculation.BenefitRestaurantDetails;
import com.rewardomain.calculation.ErrorReponse;
import com.rewardomain.calculation.proxy.BenefitRestaurantProxy;

import io.github.resilience4j.retry.annotation.Retry;

@CrossOrigin(origins = "http://localhost:4200/") 
@RestController
public class BenefitCalculationController {
	@Autowired
	private BenefitRestaurantProxy proxy;
	
	@Autowired
	private Environment environment;
	
	public ResponseEntity<ErrorReponse> defaultResponse (RuntimeException e) { 
		HttpStatusCode httpStatusCode =HttpStatusCode.valueOf(404); 
		return new ResponseEntity<ErrorReponse>(new ErrorReponse(404L, e.getMessage()), httpStatusCode);
	}
	@Retry(name="benefit-calculation", fallbackMethod ="defaultResponse")
	@GetMapping("/benefit-calculation/{merchant_number}/{dining_amount}")
	public ResponseEntity<BenefitRestaurant> getBenefitAmountFeign(@PathVariable("merchant_number") long number,
			@PathVariable("dining_amount") double amount) {
		
		BenefitRestaurantDetails benefitRestaurantDetails = proxy.getBenefitRestaurantDetails(number);
		String port = environment.getProperty("local.server.port");
		HttpStatusCode httpStatusCode ;
		if (benefitRestaurantDetails == null) {
            httpStatusCode = HttpStatusCode.valueOf(404);
        } else {
            httpStatusCode = HttpStatusCode.valueOf(200);
        }
		
		BenefitRestaurant benefitRestaurant=  new BenefitRestaurant(amount, benefitRestaurantDetails.getPercentage());
		benefitRestaurant.setExecutionChain(
				"Service benefit calculation " + port +
				" == Invoked ==> "+
				benefitRestaurantDetails.getExecutionChain());
		
		//return new ResponseEntity<BenefitRestaurantDetails> (benefitRestaurantDetails, httpStatusCode);
		return new ResponseEntity<BenefitRestaurant> (benefitRestaurant, httpStatusCode);
		
	}
	
	//old method
	@GetMapping("/benefit-calculation-rest-template/{merchant_number}/{dining_amount}")
	public BenefitRestaurant getBenefitAmount(@PathVariable("merchant_number") long number,
			@PathVariable("dining_amount") double amount) {
		HashMap<String, String> uriVariab1es = new HashMap<>();
		uriVariab1es.put("number", String.valueOf(number));
		
		ResponseEntity<BenefitRestaurantDetails> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8100/benefit-restaurant/merchants/{number}", BenefitRestaurantDetails.class,
				uriVariab1es);
		System.out.println("Body "+ responseEntity.getBody());
		BenefitRestaurantDetails benefitCalculation = responseEntity.getBody();	
		return new BenefitRestaurant(amount, benefitCalculation.getPercentage());
	}
	
	@RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptionsRequest() {
        return ResponseEntity.ok().build();
    }
	//test message
	@GetMapping("/benefit-calculation/msg")
	public String test() {
		return new String("OKKKK");
	}
}
