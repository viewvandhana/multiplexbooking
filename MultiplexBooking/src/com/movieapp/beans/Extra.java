package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Extra {

	private Long id;
	private String name;
	private Float cost;
	
	
	public Extra()
	{
		super();
	}
	
	public Extra(Long id, String extraName, Float extraCost) {
		super();
		this.id = id;
		this.name = extraName;
		this.cost = extraCost;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String extraName) {
		this.name = extraName;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float extraCost) {
		this.cost = extraCost;
	}
	
	
	
}
