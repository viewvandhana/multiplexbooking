
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

import Exception.ResponseFailureException;

import com.movieapp.beans.Movie;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.util.ObjectMapperUtil;
import com.movieapp.wrapperbeans.MovieWrapper;

@Path("/movies")
public class MovieResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMovie(MovieWrapper movieWrapper) {

		try {
			HashMap<String, Movie> movieAdded=new HashMap<String, Movie>();
			Movie movie =movieWrapper.getMovie();
			movieAdded.put("movie",new AdminAPI().addMovie(movie));
			return Response.ok(movieAdded).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
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
	public Response getAllMovies(@QueryParam("fields") String fields) {
		try {
			HashMap<String, ArrayList<Movie>> movieList=new HashMap<String, ArrayList<Movie>>();
			movieList.put("movies",new CommonAPI().getMovies(fields));
			return Response.ok(movieList).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
			
		}

	}

	@GET
	@Path("{movie_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieById(@PathParam("movie_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			HashMap<String, Movie> movie=new HashMap<String, Movie>();
			movie.put("movie", new CommonAPI().getMovieById(id,fields));
			return Response.ok(movie).build();
			} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		}
	
	}

	@PUT
	@Path("{movie_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMovie(MovieWrapper movieWrapper,@PathParam("movie_id") Long movieId) {

		try {
			Movie movie = movieWrapper.getMovie();
			movie.setId(movieId);
			HashMap<String, Movie> updatedMovie=new HashMap<String, Movie>();
			updatedMovie.put("movie", new AdminAPI().updateMovie(movie));
			return Response.ok(updatedMovie).build();
	
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 
	}

}
