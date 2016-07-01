package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Category;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Show;
import com.movieapp.beans.ShowSeat;

@JsonInclude(Include.NON_NULL)
public class MovieShowSeatResponseWrapper {

	private ArrayList<ShowSeat> showseats;
	private ArrayList<Seat> seats;
	private ArrayList<Category> categories;
	private ArrayList<MovieShow> movieShows;
	private ArrayList<Screen> screens;
	private ArrayList<Show> shows;
	private ArrayList<Movie> movies;
	
	
	public ArrayList<ShowSeat> getShowseats() {
		return showseats;
	}
	public void setShowseats(ArrayList<ShowSeat> showseats) {
		this.showseats = showseats;
	}
	public ArrayList<Seat> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	public ArrayList<MovieShow> getMovieShows() {
		return movieShows;
	}
	public void setMovieShows(ArrayList<MovieShow> movieShows) {
		this.movieShows = movieShows;
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
	public ArrayList<Movie> getMovies() {
		return movies;
	}
	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
	
	
}
