package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Ticket {

	private Long id;
	private Long movieShowID;
	private Long customerID;
	private Float totalCost;

	public Ticket()
	{
		super();
	}
	
	public Ticket(Long id, Long movieShowId, Long customerId, Float totalCost) {
		super();
		this.id = id;
		this.movieShowID = movieShowId;
		this.customerID = customerId;
		this.totalCost = totalCost;
	}
	
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMovieShowID() {
		return movieShowID;
	}
	public void setMovieShowID(Long movieShowId) {
		this.movieShowID = movieShowId;
	}
	public Long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Long customerId) {
		this.customerID = customerId;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	
	
}
