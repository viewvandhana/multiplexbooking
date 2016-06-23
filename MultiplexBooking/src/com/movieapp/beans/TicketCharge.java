package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TicketCharge {

	private Long id;
	private Long ticketID;
	private Long extraID;
	private int quantity;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTicketID() {
		return ticketID;
	}

	public void setTicketID(Long ticketID) {
		this.ticketID = ticketID;
	}

	public Long getExtraID() {
		return extraID;
	}

	public void setExtraID(Long extraID) {
		this.extraID = extraID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public TicketCharge()
	{
		super();
	}
	
	public TicketCharge(Long id, Long ticketId, Long extraId, int quantity) {
		super();
		this.id = id;
		this.ticketID = ticketId;
		this.extraID = extraId;
		this.quantity = quantity;
	}
	
	
		
}
