package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.MovieShow;

@JsonInclude(Include.NON_NULL)
public class MovieShowWrapper {

	MovieShow movieshow;
	ArrayList<MovieShow> movieshows;
	
	public MovieShow getMovieshow() {
		return movieshow;
	}

	public void setMovieshow(MovieShow movieshow) {
		this.movieshow = movieshow;
	}

	public ArrayList<MovieShow> getMovieshows() {
		return movieshows;
	}

	public void setMovieshows(ArrayList<MovieShow> movieShowsList) {
		this.movieshows = movieShowsList;
	}
	
}
