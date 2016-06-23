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

import Exception.ResponseFailureException;

import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;

@Path("/screens")
public class ScreenResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addScreen(String screen) {

		try {
			return new AdminAPI().addScreen(screen);
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
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
	public String getAllScreens() {

		try {
			
			return new CommonAPI().getScreens();
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Path("{screen_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllScreens(@PathParam("screen_id") Long screenId) {

		try {
			
					return new CommonAPI().getScreenForId(screenId);
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} 

	}

	@PUT
	@Path("{screen_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateScreen(@QueryParam("action") String action,
			String screen,@PathParam("screen_id") Long screenId) {

		try{
		if (action == null) {
			return new AdminAPI().updateScreen(screen,screenId);

		}
		else if (action.equals("modifySeats")) {
			return new AdminAPI().updateScreenSeats(screen,screenId);
			// return "Screen updation Success";
		}
		else
		{
			return new ResponseFailureException("No such operation").getErrorJson();
		}
		}
		catch(ResponseFailureException e)
		{
			return e.getErrorJson();
		}
		

		
	}

}
