package com.movieapp.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import Exception.ResponseFailureException;

import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.bo.admin.UserAPI;

@Path("/showseats")
public class ShowSeatResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getShowSeatsByMovieShowId(
			@QueryParam("movieShowId") Long msId,
			@QueryParam("ticketId") Long ticketId) {
		try {
			if (msId != null && ticketId == null) {
				return new CommonAPI().getShowSeats(msId);
			} else if (msId == null && ticketId != null) {
				return new UserAPI().getShowSeatsForTicket(ticketId);
			}
			else
			{
				return new ResponseFailureException("No such operation").getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

}
