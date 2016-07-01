package com.movieapp.rest.resources;


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
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.wrapperbeans.SeatWrapper;

@Path("/seats")
public class SeatResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSeat(SeatWrapper seatWrapper) {

		try {
			Seat seatObj = seatWrapper.getSeat();
			Seat seatInserted=new AdminAPI().addSeat(seatObj);
			return Response.ok(new AdminAPI().getSeatByID(seatInserted.getId())).build();
		} 	catch(ResponseFailureException e)
		{
			return Response.ok(e.getErrorJson()).build();
		}
		

	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSeatsForScreen(@QueryParam("fields") String fields,@QueryParam("screenId") Long screenId) {
		try {
			
			return Response.ok(new CommonAPI().getScreenForId(screenId)).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
			
		}

	}

	@GET
	@Path("{seat_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSeatById(@PathParam("seat_id") Long id) {
		
		try {
			return Response.ok(new AdminAPI().getSeatByID(id)).build();
			} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}
	
	
	}
	
	@DELETE
	@Path("{seat_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteSeat(@PathParam("seat_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteSeat(id);
				return null;
			} else {
				return new ResponseFailureException("Please provide seat id")
						.getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}
	
	
	@PUT
	@Path("{seat_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSeat(SeatWrapper seatWrapper,@PathParam("seat_id") Long seatId) {

		try {
			Seat seat = seatWrapper.getSeat();
			seat.setId(seatId);
		    new AdminAPI().updateSeat(seat);
			return Response.ok(new AdminAPI().getSeatByID(seatId)).build();
			
	
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 
	}

	
	
}
