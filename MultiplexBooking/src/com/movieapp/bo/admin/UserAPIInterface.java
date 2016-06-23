package com.movieapp.bo.admin;

import java.util.ArrayList;

import Exception.ResponseFailureException;

import com.movieapp.beans.Customer;

public interface UserAPIInterface {

	//customer
	public Customer addCustomer(Customer customer) throws ResponseFailureException;
	public Customer updateCustomer(Customer customer) throws ResponseFailureException;
	public String checkCustomer(String mailId) throws ResponseFailureException;
	
	//showseats
	public String getShowSeatsForTicket(Long ticketId) throws ResponseFailureException;
	
	//ticket
	public String bookTicket(String ticketInput) throws ResponseFailureException;
	
}
