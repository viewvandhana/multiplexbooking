package com.movieapp.rest.resources;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Exception.ResponseFailureException;

import com.movieapp.beans.Seat;
import com.movieapp.beans.Ticket;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.bo.admin.UserAPI;
import com.movieapp.util.ObjectMapperUtil;
import com.movieapp.wrapperbeans.SeatWrapper;
import com.movieapp.wrapperbeans.TicketRequestWrapper;
import com.movieapp.wrapperbeans.TicketWrapper;

@Path("/tickets")
public class TicketResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
		public Response addTicket(@QueryParam("action") String action,
			TicketRequestWrapper ticketInput) {

		try {
			if (action.equals("bookTicket")) {

				return Response.ok(new UserAPI().bookTicket(ticketInput.getTicket())).build();
			} else
				return Response.ok(new ResponseFailureException("No such operation")
						.getErrorJson()).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTicketsForMovieShow(@QueryParam("movie_show") Long msId) {
		try {
			HashMap<String, ArrayList<Ticket>> ticketList=new HashMap<String, ArrayList<Ticket>>();
			ticketList.put("tickets",new AdminAPI().getTickets(msId));
			return Response.ok(ticketList).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@GET
	@Path("{ticket_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicketById(@PathParam("ticket_id") Long ticketId) {
		try {
			return Response.ok(new CommonAPI().getTicketDetail(ticketId)).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@DELETE
	@Path("{ticket_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteTicket(@PathParam("ticket_id") Long id) {

		try {
			if (id != null) {
				new CommonAPI().deleteTicket(id);
				return null;
			} else {
				return new ResponseFailureException("Please provide ticket id")
						.getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}
	
	//can only add new extras
	@PUT
	@Path("{ticket_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTicket(TicketRequestWrapper ticketWrapper,@PathParam("ticket_id") Long seatId) {

		try {
			return Response.ok(new UserAPI().updateTicket(ticketWrapper.getTicket())).build();
	
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 
	}
	
}
