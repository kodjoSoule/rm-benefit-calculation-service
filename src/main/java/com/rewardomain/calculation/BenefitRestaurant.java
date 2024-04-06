package com.rewardomain.calculation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BenefitRestaurant {
	@JsonProperty("benefit_amount")
	private double amount;
	
	@JsonProperty("execution_chain")
	private String executionChain; 
	
	public String getExecutionChain() {
		return executionChain;
	}

	public void setExecutionChain(String executionChain) {
		this.executionChain = executionChain;
	}

	public BenefitRestaurant() {
	}

	public BenefitRestaurant(double diningAmount, double benefitPercentage) {
		this.setAmount(diningAmount * benefitPercentage * .01);
	}

	public double getAmount() {
		return amount ;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "BenefitRestaurant [amount=" + amount + "]";
	}
	
	
}
