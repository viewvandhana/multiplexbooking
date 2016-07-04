package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Extra;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Show;
import com.movieapp.beans.TicketCharge;

@JsonInclude(Include.NON_NULL)
public class TicketResponseWrapper {

	private TicketResponseJSONWrapper ticket;
	private ArrayList<TicketCharge> ticketcharges;
	private ArrayList<MovieShow> movieshows;
	private ArrayList<Screen> screens;
	private ArrayList<Show> shows;
	private ArrayList<Movie> movies;
	private ArrayList<Extra> extras;
	private ArrayList<Customer> customers;
	private ArrayList<Seat> seats;
	private ArrayList<Category> categories;
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	public ArrayList<Seat> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
	public TicketResponseJSONWrapper getTicket() {
		return ticket;
	}
	public void setTicket(TicketResponseJSONWrapper ticket) {
		this.ticket = ticket;
	}
	public ArrayList<TicketCharge> getTicketcharges() {
		return ticketcharges;
	}
	public void setTicketcharges(ArrayList<TicketCharge> ticketcharges) {
		this.ticketcharges = ticketcharges;
	}
	public ArrayList<MovieShow> getMovieshows() {
		return movieshows;
	}
	public void setMovieshows(ArrayList<MovieShow> movieShows) {
		this.movieshows = movieShows;
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
	public ArrayList<Extra> getExtras() {
		return extras;
	}
	public void setExtras(ArrayList<Extra> extras) {
		this.extras = extras;
	}
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
	public String obtainSeatNameSring()
	{
		String seatNameStr="";
		 for(int i=0;i<seats.size();i++)
		    {
		    	
		    	seatNameStr=seatNameStr+seats.get(i).getName();
		    }
		 return seatNameStr;
	}
	
	
}
