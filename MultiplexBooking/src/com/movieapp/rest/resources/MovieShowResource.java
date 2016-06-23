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

import org.json.JSONArray;
import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.movieapp.beans.MovieShow;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.util.ObjectMapperUtil;

@Path("/movieshows")
public class MovieShowResource {


		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
			public String addMovieShow(String movieShow) {

			try{
				JSONObject j=new JSONObject(movieShow);	
				JSONArray movieshowArray=j.optJSONArray("movieshows");
				ArrayList<MovieShow> movieShowList=new ArrayList<MovieShow>();
				for(int i=0;i<movieshowArray.length();i++)
				{
				movieShowList.add(ObjectMapperUtil.getMapper().readValue(movieshowArray.optJSONObject(i).toString(),MovieShow.class));
				}
				return new AdminAPI().addMovieShow(movieShowList);
				}
			catch(ResponseFailureException e)
			{
				return e.getErrorJson();
			}
			catch (Exception e) {
				return new ResponseFailureException(e.getMessage()).getErrorJson();
			}
		}

		@DELETE
		@Path("{movie_show_id}")
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteMovieShow(@PathParam("movie_show_id") Long id) {

			try{
			if (id != null) {
				new AdminAPI().deleteMovieShow(id);
				//return "MovieShow Deletion Success";
				return null;
			} else {
				return new ResponseFailureException("Please provide movie show id").getErrorJson();
			}
			}
			catch(ResponseFailureException e)
			{
				return e.getErrorJson();
			}
		

		}
		
		@GET
		@Path("{movie_show_id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getMovieShowById(@PathParam("movie_show_id") Long id) {
			
			try {
				return new CommonAPI().getMovieShowsByMovieShowId(id);
			} catch (ResponseFailureException e) {
				return e.getErrorJson();
			}
		
		}

//		@GET
//		@Produces(MediaType.TEXT_PLAIN)
//		public String getAllmovieShows() {
//		  return ObjectMapperUtil.getCustomMappedString("movieshows", AdminAPI.getMovieShows());
//		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String getMovieShowsByDate(@QueryParam("date") String date,@QueryParam("movie") Long movieId,@QueryParam("screen") Long screenId) {
		
			try{
			
			return new CommonAPI().getMovieShowsByFilters(date,movieId,screenId);
			}
			catch(ResponseFailureException e)
			{
				return e.getErrorJson();
			}
		
		}

		
		@PUT
		@Path("{movie_show_id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String updatemovieShows(String movieShow,@PathParam("movie_show_id") Long msID) {
			try{
				JSONObject j=new JSONObject(movieShow);	
				JSONObject movieShowJson=j.optJSONObject("movieshow");
				MovieShow movieShowObj = ObjectMapperUtil.getMapper().readValue(movieShowJson.toString(),MovieShow.class);
				movieShowObj.setId(msID);
				return new AdminAPI().updateMovieShow(movieShowObj);
				}
				catch(ResponseFailureException e)
				{
					return e.getErrorJson();
				}
			  catch(Exception e)
			{
				  return new ResponseFailureException(e.getMessage()).getErrorJson();
			}
			
		}


}
