package com.movieapp.bo.admin;

import java.util.ArrayList;

import Exception.ResponseFailureException;

import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Extra;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Show;
import com.movieapp.beans.ShowSeat;
import com.movieapp.wrapperbeans.MovieShowResponseWrapper;
import com.movieapp.wrapperbeans.MovieShowSeatResponseWrapper;
import com.movieapp.wrapperbeans.ScreenSeatResponseWrapper;
import com.movieapp.wrapperbeans.TicketResponseWrapper;

public interface CommonAPIInterface {

	//show
	public ArrayList<Show> getShows(String fields) throws ResponseFailureException;
	public Show getShowsById(Long id,String fields) throws ResponseFailureException;
	
	//screen
	public ScreenSeatResponseWrapper getScreens() throws ResponseFailureException;
	public ScreenSeatResponseWrapper getScreenForId(Long screenId) throws ResponseFailureException;
	
	//movie
	public ArrayList<Movie> getMovies(String fields) throws ResponseFailureException;
	public Movie getMovieById(Long movieId,String fields) throws ResponseFailureException;
	
	//movieshows
	public ArrayList<MovieShow> getMovieShows() throws ResponseFailureException;
	public MovieShowResponseWrapper getMovieShowsByMovieShowId(Long msId) throws ResponseFailureException;
	public MovieShowResponseWrapper getMovieShowsByFilters(String date,Long movieId,Long screenId) throws ResponseFailureException;
	
	//customer
	public Customer getCustomerById(Long id,String fields) throws ResponseFailureException;
	
	//customer
	public ArrayList<Category> getCategories(String fields) throws ResponseFailureException;
	public Category getCategoryById(Long id,String fields) throws ResponseFailureException;
	
	//extra
	public ArrayList<Extra> getExtras(String fields) throws ResponseFailureException;
	public Extra getExtraById(Long id,String fields) throws ResponseFailureException;
	
	//showseat
	public MovieShowSeatResponseWrapper getShowSeats(Long msId) throws ResponseFailureException;
	public MovieShowSeatResponseWrapper getShowSeatByID(ArrayList<Long> showSeatList) throws ResponseFailureException;
		
	
	//ticket
	public TicketResponseWrapper getTicketDetail(Long ticketId) throws ResponseFailureException;
	public void deleteTicket(Long ticketId) throws ResponseFailureException;
}
