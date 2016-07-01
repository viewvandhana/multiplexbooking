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

import com.movieapp.beans.Extra;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.util.ObjectMapperUtil;
import com.movieapp.wrapperbeans.ExtraWrapper;

@Path("/extras")
public class ExtraResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addExtra(ExtraWrapper extraWrapper) {

		try {
			Extra extraObj = extraWrapper.getExtra();
			HashMap<String, Extra> extraAdded=new HashMap<String, Extra>();
			extraAdded.put("extra",new AdminAPI().addExtra(extraObj));
			return Response.ok(extraAdded).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 

	}

	@DELETE
	@Path("{extra_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteExtra(@PathParam("extra_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteExtra(id);
				//return "Extra Deletion Success";
				return null;
			} else {
				return new ResponseFailureException("Please provide extra id")
						.getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllExtras(@QueryParam("fields") String fields) {
		try {
			HashMap<String, ArrayList<Extra>> extraList=new HashMap<String, ArrayList<Extra>>();
			extraList.put("extras",new CommonAPI().getExtras(fields));
			return Response.ok(extraList).build();
		
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@PUT
	@Path("{extra_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateExtra(ExtraWrapper extraWrapper,@PathParam("extra_id") Long extraId) {

		try {
			Extra extraObj = extraWrapper.getExtra();
			extraObj.setId(extraId);
			HashMap<String, Extra> extraUpdated=new HashMap<String,Extra>();
			extraUpdated.put("extra",new AdminAPI().updateExtra(extraObj));
			return Response.ok(extraUpdated).build();
			
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 

	}

	@GET
	@Path("{extra_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExtraById(@PathParam("extra_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			HashMap<String, Extra> extra=new HashMap<String,Extra>();
			extra.put("extra",new CommonAPI().getExtraById(id, fields));
			return Response.ok(extra).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}
	
	}
}
