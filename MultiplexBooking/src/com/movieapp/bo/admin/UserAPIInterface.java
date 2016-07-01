package com.movieapp.bo.admin;

import Exception.ResponseFailureException;

import com.movieapp.beans.Customer;
import com.movieapp.wrapperbeans.MovieShowSeatResponseWrapper;
import com.movieapp.wrapperbeans.TicketResponseWrapper;
import com.movieapp.wrapperbeans.TicketWrapper;

public interface UserAPIInterface {

	//customer
	public Customer addCustomer(Customer customer) throws ResponseFailureException;
	public Customer updateCustomer(Customer customer) throws ResponseFailureException;
	public Customer checkCustomer(String mailId) throws ResponseFailureException;
	
	//showseats
	public MovieShowSeatResponseWrapper getShowSeatsForTicket(Long ticketId) throws ResponseFailureException;
	
	//ticket
	public TicketResponseWrapper bookTicket(TicketWrapper ticketInput) throws ResponseFailureException;
	public TicketResponseWrapper updateTicket(TicketWrapper ticketWrapper) throws ResponseFailureException;
	
}
