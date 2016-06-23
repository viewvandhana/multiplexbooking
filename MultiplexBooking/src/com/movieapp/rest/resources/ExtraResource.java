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

import com.movieapp.beans.Extra;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.util.ObjectMapperUtil;

@Path("/extras")
public class ExtraResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addExtra(String extra) {

		try {
			JSONObject j = new JSONObject(extra);
			JSONObject extraJson = j.optJSONObject("extra");
			Extra extraObj = ObjectMapperUtil.getMapper().readValue(
					extraJson.toString(), Extra.class);
			return ObjectMapperUtil.getCustomMappedString("extra",
					new AdminAPI().addExtra(extraObj));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
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
	public String getAllExtras(@QueryParam("fields") String fields) {
		try {
			return ObjectMapperUtil.getCustomMappedString("extras",
					new CommonAPI().getExtras(fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@PUT
	@Path("{extra_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateExtra(String extra,@PathParam("extra_id") Long extraId) {

		try {
			JSONObject j = new JSONObject(extra);
			JSONObject extraJson = j.optJSONObject("extra");
			Extra extraObj = ObjectMapperUtil.getMapper().readValue(
					extraJson.toString(), Extra.class);
			extraObj.setId(extraId);
			return ObjectMapperUtil.getCustomMappedString("extra",
					new AdminAPI().updateExtra(extraObj));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
		}

	}

	@GET
	@Path("{extra_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getExtraById(@PathParam("extra_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			return ObjectMapperUtil.getCustomMappedString("extra",
					new CommonAPI().getExtraById(id, fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}
	
	}
}
