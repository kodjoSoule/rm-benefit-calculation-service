package com.rewardomain.calculation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BenefitRestaurantDetails extends BenefitRestaurant{
	private int id;
	@JsonProperty("merchant_number")
	private long number;
	private String name;
	@JsonProperty("benefit_percentage")
	private double percentage;
	@JsonProperty("benefit_availability_policy")
	private String availability;
	
	public BenefitRestaurantDetails() {}

	public BenefitRestaurantDetails(int id, long number, String name, double percentage, String availability) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.percentage = percentage;
		this.availability = availability;
	}

	public int getId() {
		return id;
	}

	public long getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public double getPercentage() {
		return percentage;
	}

	public String getAvailability() {
		return availability;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}

	
	
}
