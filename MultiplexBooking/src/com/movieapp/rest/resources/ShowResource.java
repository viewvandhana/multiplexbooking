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

import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.movieapp.beans.Show;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
// Plain old Java Object it does not extend as class or implements 
// an interface
import com.movieapp.util.ObjectMapperUtil;

@Path("/shows")
public class ShowResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addShow(String show) {
		try {
			JSONObject j = new JSONObject(show);
			JSONObject showJson = j.optJSONObject("show");
			Show showObj = ObjectMapperUtil.getMapper().readValue(
					showJson.toString(), Show.class);
			return ObjectMapperUtil.getCustomMappedString("show",
					new AdminAPI().addShow(showObj));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
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
	public String getAllShows(@QueryParam("fields") String fields) {
		try {
			return ObjectMapperUtil.getCustomMappedString("shows",
					new CommonAPI().getShows(fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@PUT
	@Path("{show_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateShow(String show,@PathParam("show_id") Long showId) {

		try {
			JSONObject j = new JSONObject(show);
			JSONObject showJson = j.optJSONObject("show");
			Show showObj = ObjectMapperUtil.getMapper().readValue(
					showJson.toString(), Show.class);
			showObj.setId(showId);
			return ObjectMapperUtil.getCustomMappedString("show",
					new AdminAPI().updateShow(showObj));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
		}

	}
	@GET
	@Path("{show_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getShowById(@PathParam("show_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			return ObjectMapperUtil.getCustomMappedString("show",
					new CommonAPI().getShowsById(id, fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}
	
	}
}
