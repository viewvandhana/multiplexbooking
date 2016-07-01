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

import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.wrapperbeans.ScreenSeatWrapper;
import com.movieapp.wrapperbeans.ScreenWrapper;

@Path("/screens")
public class ScreenResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScreen(ScreenWrapper screenWrapper) {

		try {
			return Response.ok(new AdminAPI().addScreen(screenWrapper.getScreen())).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@DELETE
	@Path("{screen_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteScreen(@PathParam("screen_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteScreen(id);
				//return "Screen Deletion Success";
				return null;
			} else {
				return new ResponseFailureException("Please provide screen id")
						.getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllScreens() {

		try {
			
			return Response.ok(new CommonAPI().getScreens()).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@GET
	@Path("{screen_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScreenById(@PathParam("screen_id") Long screenId) {

		try {
			
		  return Response.ok(new CommonAPI().getScreenForId(screenId)).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 

	}

	@PUT
	@Path("{screen_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateScreen(@QueryParam("action") String action,
			ScreenWrapper screenWrapper,@PathParam("screen_id") Long screenId) {

		try{
		if (action == null) {
			return Response.ok(new AdminAPI().updateScreen(screenWrapper.getScreen(),screenId)).build();

		}
		else if (action.equals("modifySeats")) {
			return Response.ok(new AdminAPI().updateScreenSeats(screenWrapper.getScreen(),screenId)).build();
			// return "Screen updation Success";
		}
		else
		{
			return Response.ok(new ResponseFailureException("No such operation").getErrorJson()).build();
		}
		}
		catch(ResponseFailureException e)
		{
			return Response.ok(e.getErrorJson()).build();
		}
		

		
	}

}
