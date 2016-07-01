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

import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.movieapp.beans.Show;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
// Plain old Java Object it does not extend as class or implements 
// an interface
import com.movieapp.util.ObjectMapperUtil;
import com.movieapp.wrapperbeans.ShowWrapper;

@Path("/shows")
public class ShowResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addShow(ShowWrapper showWrapper) {
		try {
			Show showObj = showWrapper.getShow();
			HashMap<String,Show> show=new HashMap<String, Show>();
			show.put("show",new AdminAPI().addShow(showObj));
			return Response.ok(show).build();
	
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@DELETE
	@Path("{show_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteShow(@PathParam("show_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteShow(id);
				//return "Show Deletion Success";
				return null;
			} else {
				return new ResponseFailureException("Please provide show id")
						.getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllShows(@QueryParam("fields") String fields) {
		try {
			HashMap<String, ArrayList<Show>> shows=new HashMap<String, ArrayList<Show>>();
			shows.put("shows", new CommonAPI().getShows(fields));
			return Response.ok(shows).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@PUT
	@Path("{show_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateShow(ShowWrapper showWrapper,@PathParam("show_id") Long showId) {

		try {
			
			Show showObj = showWrapper.getShow();
			showObj.setId(showId);
			HashMap<String,Show> show=new HashMap<String, Show>();
			show.put("show",new AdminAPI().updateShow(showObj));
			return Response.ok(show).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 

	}
	@GET
	@Path("{show_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShowById(@PathParam("show_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			HashMap<String,Show> show=new HashMap<String, Show>();
			show.put("show",new CommonAPI().getShowsById(id, fields));
			return Response.ok(show).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}
	
	}
}
