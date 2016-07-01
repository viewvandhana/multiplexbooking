package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Ticket;

@JsonInclude(Include.NON_NULL)
public class TicketResponseJSONWrapper {

	private Long id;
	private Long movieShowID;
	private Long customerID;
	private Float totalCost;
	private ArrayList<Long> seats;
	private ArrayList<Long> ticketcharges;
	
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
	public ArrayList<Long> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Long> seats) {
		this.seats = seats;
	}
	public ArrayList<Long> getTicketcharges() {
		return ticketcharges;
	}
	public void setTicketcharges(ArrayList<Long> ticketcharges) {
		this.ticketcharges = ticketcharges;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Ticket constructTicket()
	{
		Ticket ticket=new Ticket();
		ticket.setCustomerID(customerID);
		ticket.setMovieShowID(movieShowID);
		ticket.setTotalCost(totalCost);
		return ticket;
	}
	
	public void setTicketObj(Ticket ticket,ArrayList<Long> seats,ArrayList<Long> ticketCharges)
	{
		setId(ticket.getId());
		setCustomerID(ticket.getCustomerID());
		setMovieShowID(ticket.getMovieShowID());
		setTotalCost(ticket.getTotalCost());
		setSeats(seats);
		setTicketcharges(ticketCharges);
		
	}

}
