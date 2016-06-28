package com.movieapp.bo.admin;

import java.lang.reflect.Field;
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
				DataAccess.getTransactionManager().begin();
			Customer customerInserted=((Customer)ServiceInstance.getCustomerService().insert(customer));
			DataAccess.getTransactionManager().commit();
			return customerInserted;
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
				throw new ResponseFailureException(e.getMessage());
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
			DataAccess.getTransactionManager().begin();	
		Customer customerUpdated= (Customer)((CustomerDAOMickeyImpl)ServiceInstance.getCustomerService()).updateCustomerById(customer);
		DataAccess.getTransactionManager().commit();
		 return customerUpdated;
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
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
	}

	@Override
	public String checkCustomer(String mailId) throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((CustomerDAOMickeyImpl)ServiceInstance.getCustomerService()).checkCustomer(mailId);
		
	}

	@Override
	public String getShowSeatsForTicket(Long ticketId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).getShowSeatsByTicketId(ticketId);
	}

	@Override
	public String bookTicket(String ticketInput)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			JSONObject ticketInputObj=new JSONObject(ticketInput);
			JSONObject ticketDetailObj=ticketInputObj.optJSONObject("ticket");
			
			Ticket ticket =new Ticket(null,ticketDetailObj.optLong("movieShowID") ,ticketDetailObj.optLong("customerID"),Float.parseFloat(ticketDetailObj.optString("totalCost")));
			Ticket ticketInserted=addTicket(ticket);
			JSONArray showSeatsArray=ticketDetailObj.optJSONArray("showseats");
			for(int i=0;i<showSeatsArray.length();i++)
			{
				((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).updateShowSeatByMovieShowId(ticketInserted.getMovieShowID(),ticketInserted.getId(),showSeatsArray.optLong(i));
				
			}
			JSONArray ticketChargesArray=ticketDetailObj.optJSONArray("ticketcharges");
			ArrayList<TicketCharge> ticketChargesList=new ArrayList<TicketCharge>();
			for(int i=0;i<ticketChargesArray.length();i++)
			{
			JSONObject chargeObj=ticketChargesArray.optJSONObject(i);	
			ticketChargesList.add(new TicketCharge(null, ticketInserted.getId(),chargeObj.optLong("extraID") , chargeObj.optInt("quantity")));
			}
			ServiceInstance.getTicketChargeService().insertMultipleRows(ticketChargesList);
			HashMap<String, String> ticketDetail=JoinDAO.getTicketDetail(ticketInserted.getId(),ticketChargesList.size()==0?false:true);
			DataAccess.getTransactionManager().commit();
			sendSms(ticketInserted.getId(),ticketInserted.getCustomerID(),ticket.getTotalCost(),JoinDAO.getBookTicketMessage(ticketInserted.getMovieShowID()),ticketDetail.get("seatNames"));
			return ticketDetail.get("finalResponse");
		}
		catch(DataAccessException e)
		{
			System.out.println("exception------>"+e.toString());
			System.out.println("exceptioncode------>"+e.getErrorCode());
			
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
