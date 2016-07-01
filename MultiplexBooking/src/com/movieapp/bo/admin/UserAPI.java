package com.movieapp.bo.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.SystemException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Ticket;
import com.movieapp.beans.TicketCharge;
import com.movieapp.daoimpl.CustomerDAOMickeyImpl;
import com.movieapp.daoimpl.JoinDAO;
import com.movieapp.daoimpl.ShowSeatDAOMickeyImpl;
import com.movieapp.wrapperbeans.MovieShowSeatResponseWrapper;
import com.movieapp.wrapperbeans.TicketResponseWrapper;
import com.movieapp.wrapperbeans.TicketWrapper;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;

public class UserAPI implements UserAPIInterface{

	@Override
	public Customer addCustomer(Customer customer)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		if(((CustomerDAOMickeyImpl)ServiceInstance.getCustomerService()).getCustomer(customer.getEmail())==null)
		{
			try{
			Customer customerInserted=((Customer)ServiceInstance.getCustomerService().insert(customer));
			return customerInserted;
			}
			catch(Exception e)
			{
				throw new ResponseFailureException(e.getMessage());
			}
			
		}
	throw new ResponseFailureException("Customer already exists");
	}

	@Override
	public Customer updateCustomer(Customer customer)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
		Customer customerUpdated= (Customer)((CustomerDAOMickeyImpl)ServiceInstance.getCustomerService()).updateCustomerById(customer);
		 return customerUpdated;
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
	}

	@Override
	public Customer checkCustomer(String mailId) throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((CustomerDAOMickeyImpl)ServiceInstance.getCustomerService()).checkCustomer(mailId);
		
	}

	@Override
	public MovieShowSeatResponseWrapper getShowSeatsForTicket(Long ticketId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).getShowSeatsByTicketId(ticketId);
	}

	@Override
	public TicketResponseWrapper bookTicket(TicketWrapper ticketWrapper)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			Ticket ticket =ticketWrapper.constructTicket();
			Ticket ticketInserted=addTicket(ticket);
			ArrayList<Long> showSeatsList=ticketWrapper.getShowseats();
			for(int i=0;i<showSeatsList.size();i++)
			{
				((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).updateShowSeatByMovieShowId(ticketInserted.getMovieShowID(),ticketInserted.getId(),showSeatsList.get(i));
				
			}
			ArrayList<TicketCharge> ticketChargesList=ticketWrapper.getTicketcharges();
			ServiceInstance.getTicketChargeService().insertMultipleRows(ticketChargesList);
			TicketResponseWrapper ticketResponseWrapper=JoinDAO.getTicketDetail(ticketInserted.getId());
			DataAccess.getTransactionManager().commit();
			sendSms(ticketInserted.getId(),ticketInserted.getCustomerID(),ticket.getTotalCost(),JoinDAO.getBookTicketMessage(ticketInserted.getMovieShowID()),ticketResponseWrapper.obtainSeatNameSring());
			return ticketResponseWrapper;
		}
		catch(DataAccessException e)
		{
			
			try {
				DataAccess.getTransactionManager().rollback();
				} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(e.getMessage().contains("dirty-write protected"))
			{
					throw new ResponseFailureException("Ticket(s) booked for selected seat(s)");
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

	}
	
	@Override
	public TicketResponseWrapper updateTicket(TicketWrapper ticketWrapper)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			Ticket ticket =ticketWrapper.constructTicket();
			ArrayList<TicketCharge> ticketChargesList=ticketWrapper.getTicketcharges();
			ServiceInstance.getTicketChargeService().insertMultipleRows(ticketChargesList);
			TicketResponseWrapper ticketResponseWrapper=JoinDAO.getTicketDetail(ticket.getId());
			sendSms(ticket.getId(),ticket.getCustomerID(),ticket.getTotalCost(),JoinDAO.getBookTicketMessage(ticket.getMovieShowID()),ticketResponseWrapper.obtainSeatNameSring());
			return ticketResponseWrapper;
		}
		
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

	}

	public Ticket addTicket(Ticket ticket) throws DataAccessException,ResponseFailureException{

		Ticket ticketBooked= (Ticket)ServiceInstance.getTicketService().insert(ticket);
		return ticketBooked;
	}
	
	public static void sendSms(Long ticketId,Long customerId,Float cost,String showDetails,String seatDetails) throws ResponseFailureException
	{
		Customer customer=(ServiceInstance.getCustomerService().getById(customerId,null));
		TwilioRestClient client = new TwilioRestClient("AC0297b901ab24309796b516fc7b917c77", "9a3ecd709228bd947baf2c0ed79264c1");   
		// Build the parameters   
		 Account account = client.getAccount();

	      //  SmsFactory messageFactory = account.getSmsFactory();
	        MessageFactory messageFactory=account.getMessageFactory();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("To", customer.getPhoneNumber())); // Replace with a valid phone number for your account.
	        params.add(new BasicNameValuePair("From", "+19193440112 ")); // Replace with a valid phone number for your account.
	        params.add(new BasicNameValuePair("Body", "Hi "+customer.getName()+".Ticket booked.Your booking id:"+ticketId+"\n Booking details \n"+showDetails+"\n "+seatDetails));
	  //      params.add(new BasicNameValuePair("Body", "Hi vaishu.Ticket booked.Your booking id:"));
	        try {
				messageFactory.create(params);
			} catch (TwilioRestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
