package com.movieapp.wrapperbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Movie;
@JsonInclude(Include.NON_NULL)
public class MovieWrapper {

	private Movie movie;

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movieObj) {
		this.movie = movieObj;
	}
	
	
	
	
}
