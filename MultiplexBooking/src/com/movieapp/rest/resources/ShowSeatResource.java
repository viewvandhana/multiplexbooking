package com.movieapp.rest.resources;

import java.util.ArrayList;

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

import com.adventnet.persistence.DataAccessException;
import com.movieapp.beans.ShowSeat;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.bo.admin.UserAPI;
import com.movieapp.wrapperbeans.ShowSeatWrapper;

@Path("/showseats")
public class ShowSeatResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowSeatsByMovieShowId(
			@QueryParam("movieShowId") Long msId,
			@QueryParam("ticketId") Long ticketId) {
		try {
			if (msId != null && ticketId == null) {
				return Response.ok(new CommonAPI().getShowSeats(msId)).build();
			} else if (msId == null && ticketId != null) {
				return Response.ok(new UserAPI().getShowSeatsForTicket(ticketId)).build();
			}
			else
			{
				return Response.ok(new ResponseFailureException("No such operation").getErrorJson()).build();
			}
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addShowSeat(ShowSeatWrapper showSeatWrapper) {

		try {
			ArrayList<ShowSeat> showSeatList = showSeatWrapper.getShowseats();
			ArrayList<ShowSeat> insertedShowSeats=new AdminAPI().addShowSeat(showSeatList);
			ArrayList<Long> showSeatIds=new ArrayList<Long>();
			for(int i=0;i<insertedShowSeats.size();i++)
			{
				showSeatIds.add(insertedShowSeats.get(i).getId());
			}
			return Response.ok(new CommonAPI().getShowSeatByID(showSeatIds)).build();
		} 	catch(ResponseFailureException e)
		{
			return Response.ok(e.getErrorJson()).build();
		}
		catch(DataAccessException e)
		{
			return Response.ok(new ResponseFailureException(e.getMessage()).getErrorJson()).build();
		}
		

	}
	
	@GET
	@Path("{showseat_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowSeatById(@PathParam("showseat_id") Long id) {
		
		try {
			ArrayList<Long> showSeatIds=new ArrayList<Long>();
			showSeatIds.add(id);
			return Response.ok(new CommonAPI().getShowSeatByID(showSeatIds)).build();
			} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}
	
	
	}

	

	@DELETE
	@Path("{showseat_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteShowSeat(@PathParam("showseat_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteShowSeat(id);
				return null;
			} else {
				return new ResponseFailureException("Please provide show seat id")
						.getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}
	
	@PUT
	@Path("{showseat_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateShowSeat(ShowSeatWrapper showSeatWrapper,@PathParam("showseat_id") Long showSeatId) {

		try {
			ShowSeat showSeat = showSeatWrapper.getShowseat();
			showSeat.setId(showSeatId);
			new AdminAPI().updateShowSeat(showSeat);
			ArrayList<Long> showSeatIds=new ArrayList<Long>();
			showSeatIds.add(showSeatId);
			return Response.ok(new CommonAPI().getShowSeatByID(showSeatIds)).build();
		
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 
	}

}
