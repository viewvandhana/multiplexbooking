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

import com.movieapp.beans.MovieShow;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.wrapperbeans.MovieShowWrapper;

@Path("/movieshows")
public class MovieShowResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovieShow(MovieShowWrapper movieShowWrapper) {

		try {
			ArrayList<MovieShow> movieShows=new ArrayList<MovieShow>();
			MovieShow ms=movieShowWrapper.getMovieshow();
			if(ms!=null)
			{
			movieShows.add(ms);
			}
			else
			{
				movieShows=movieShowWrapper.getMovieshows();
			}
			return Response.ok(new AdminAPI().addMovieShow(movieShows)).build();	
				
			
			
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@DELETE
	@Path("{movie_show_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteMovieShow(@PathParam("movie_show_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteMovieShow(id);
				// return "MovieShow Deletion Success";
				return null;
			} else {
				return new ResponseFailureException(
						"Please provide movie show id").getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Path("{movie_show_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieShowById(@PathParam("movie_show_id") Long id) {

		try {
			return Response.ok(new CommonAPI().getMovieShowsByMovieShowId(id)).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	// @GET
	// @Produces(MediaType.TEXT_PLAIN)
	// public String getAllmovieShows() {
	// return ObjectMapperUtil.getCustomMappedString("movieshows",
	// AdminAPI.getMovieShows());
	// }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieShowsByDate(@QueryParam("date") String date,
			@QueryParam("movie") Long movieId,
			@QueryParam("screen") Long screenId) {

		try {

			return Response.ok(new CommonAPI().getMovieShowsByFilters(date, movieId,
					screenId)).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

	@PUT
	@Path("{movie_show_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMovieShow(MovieShowWrapper movieShowWrapper,
			@PathParam("movie_show_id") Long msID) {
		try {
			MovieShow movieShowObj = movieShowWrapper.getMovieshow();
			movieShowObj.setId(msID);
			return Response.ok(new AdminAPI().updateMovieShow(movieShowObj)).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}

	}

}
