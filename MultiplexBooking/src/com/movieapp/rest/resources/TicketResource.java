package com.movieapp.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import Exception.ResponseFailureException;

import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.bo.admin.UserAPI;
import com.movieapp.util.ObjectMapperUtil;

@Path("/tickets")
public class TicketResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
		public String addTicket(@QueryParam("action") String action,
			String ticketInput) {

		try {
			if (action.equals("bookTicket")) {

				return new UserAPI().bookTicket(ticketInput);
			} else
				return new ResponseFailureException("No such operation")
						.getErrorJson();
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllTicketsForMovieShow(@QueryParam("movie_show") Long msId) {
		try {
			return ObjectMapperUtil.getCustomMappedString("tickets",
					new AdminAPI().getTickets(msId));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Path("{ticket_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTicketById(@PathParam("ticket_id") Long ticketId) {
		try {
			return new CommonAPI().getTicketDetail(ticketId);
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

}
