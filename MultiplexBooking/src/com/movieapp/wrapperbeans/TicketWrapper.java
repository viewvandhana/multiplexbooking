package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Ticket;
import com.movieapp.beans.TicketCharge;

@JsonInclude(Include.NON_NULL)
public class TicketWrapper {

	private Long movieShowID;
	private Long customerID;
	private Float totalCost;
	private ArrayList<Long> showseats;
	private ArrayList<TicketCharge> ticketcharges;
	
	public Long getMovieShowID() {
		return movieShowID;
	}
	public void setMovieShowID(Long movieShowID) {
		this.movieShowID = movieShowID;
	}
	public Long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	public ArrayList<Long> getShowseats() {
		return showseats;
	}
	public void setShowseats(ArrayList<Long> showseats) {
		this.showseats = showseats;
	}
	public ArrayList<TicketCharge> getTicketcharges() {
		return ticketcharges;
	}
	public void setTicketcharges(ArrayList<TicketCharge> ticketcharges) {
		this.ticketcharges = ticketcharges;
	}
	
	public Ticket constructTicket()
	{
		Ticket ticket=new Ticket();
		ticket.setCustomerID(customerID);
		ticket.setMovieShowID(movieShowID);
		ticket.setTotalCost(totalCost);
		return ticket;
	}
	

}
