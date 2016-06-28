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

import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.movieapp.beans.Movie;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.util.ObjectMapperUtil;

@Path("/movies")
public class MovieResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(String jObj) {

		try {
			JSONObject j = new JSONObject(jObj);
			JSONObject movieObj = j.optJSONObject("movie");
			Movie movie = ObjectMapperUtil.getMapper().readValue(
					movieObj.toString(), Movie.class);
			
			String response= ObjectMapperUtil.getCustomMappedString("movie",
					new AdminAPI().addMovie(movie));
			return Response.ok(response).build();
		} catch (ResponseFailureException e) {
			
			String error= e.getErrorJson();
			return Response.status(422).entity(error).build();
		} catch (Exception e) {
			
			String error=new ResponseFailureException(e.getMessage()).getErrorJson();
			return Response.status(422).entity(error).build();
		}

	}
	


	@DELETE
	@Path("{movie_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteMovie(@PathParam("movie_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteMovie(id);
				//return "Movie Deletion Success";
				return null;
			} else {
				return new ResponseFailureException("Please provide movie id")
						.getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllMovies(@QueryParam("fields") String fields) {
		try {
			return ObjectMapperUtil.getCustomMappedString("movies",
					new CommonAPI().getMovies(fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Path("{movie_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMovieById(@PathParam("movie_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			return ObjectMapperUtil.getCustomMappedString("movie",
					new CommonAPI().getMovieById(id,fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}
	
	}

	@PUT
	@Path("{movie_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateMovie(String jObj,@PathParam("movie_id") Long movieId) {

		try {
			JSONObject j = new JSONObject(jObj);
			JSONObject movieObj = j.optJSONObject("movie");
			Movie movie = ObjectMapperUtil.getMapper().readValue(
					movieObj.toString(), Movie.class);
			movie.setId(movieId);
			return ObjectMapperUtil.getCustomMappedString("movie",
					new AdminAPI().updateMovie(movie));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
		}
	}

}
