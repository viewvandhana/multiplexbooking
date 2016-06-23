package com.movieapp.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.movieapp.beans.Seat;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.util.ObjectMapperUtil;

@Path("/seats")
public class SeatResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addSeat(String seat) {

		try {
			JSONObject j = new JSONObject(seat);
			JSONObject seatJson = j.optJSONObject("sesat");
			Seat seatObj = ObjectMapperUtil.getMapper().readValue(
					seatJson.toString(), Seat.class);
			return ObjectMapperUtil.getCustomMappedString("seat",
					new AdminAPI().addSeat(seatObj));
		} 	catch(ResponseFailureException e)
		{
			return e.getErrorJson();
		}
		catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
		}

	}

}