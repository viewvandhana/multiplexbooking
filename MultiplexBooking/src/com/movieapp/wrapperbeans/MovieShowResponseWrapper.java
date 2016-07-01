package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Show;

@JsonInclude(Include.NON_NULL)
public class MovieShowResponseWrapper {

	ArrayList<MovieShow> movieshows;
	MovieShow movieshow;
	ArrayList<Screen> screens;
	ArrayList<Show> shows;
	ArrayList<Movie> movies;
	
	public MovieShow getMovieshow() {
		return movieshow;
	}
	public void setMovieshow(MovieShow movieshow) {
		this.movieshow = movieshow;
	}
	public ArrayList<Movie> getMovies() {
		return movies;
	}
	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
	public ArrayList<MovieShow> getMovieshows() {
		return movieshows;
	}
	public void setMovieshows(ArrayList<MovieShow> movieshows) {
		this.movieshows = movieshows;
	}
	public ArrayList<Screen> getScreens() {
		return screens;
	}
	public void setScreens(ArrayList<Screen> screens) {
		this.screens = screens;
	}
	public ArrayList<Show> getShows() {
		return shows;
	}
	public void setShows(ArrayList<Show> shows) {
		this.shows = shows;
	}
	
}
