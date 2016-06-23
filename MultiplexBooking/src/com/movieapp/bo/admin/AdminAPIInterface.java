package com.movieapp.bo.admin;

import java.util.ArrayList;

import Exception.ResponseFailureException;

import com.adventnet.persistence.DataAccessException;
import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Extra;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Show;
import com.movieapp.beans.ShowSeat;
import com.movieapp.beans.Ticket;

public interface AdminAPIInterface {

	//show
	public Show addShow(Show show) throws ResponseFailureException;
	public void deleteShow(long id) throws ResponseFailureException;
	public Show updateShow(Show show) throws ResponseFailureException;
	
	//screen
	public String addScreen(String screenObj) throws ResponseFailureException;
	public void deleteScreen(long id) throws ResponseFailureException;
	public String updateScreen(String screenObj,Long screenId) throws ResponseFailureException;
	public String updateScreenSeats(String screenObj,Long screenId) throws ResponseFailureException;
	
	//movie
	public  Movie addMovie(Movie movie) throws ResponseFailureException;
	public void deleteMovie(long id) throws ResponseFailureException;
	public Movie updateMovie(Movie movie) throws ResponseFailureException;
	
	//movieShow
	public String addMovieShow(ArrayList<MovieShow> movieShowList) throws ResponseFailureException;
	public void deleteMovieShow(long id) throws ResponseFailureException;
	public String updateMovieShow(MovieShow movieShow) throws ResponseFailureException;
	
	//showSeat
	public ArrayList<ShowSeat> addShowSeat(ArrayList<ShowSeat> showSeats) throws DataAccessException,ResponseFailureException;
	public void deleteShowSeat(long id) throws ResponseFailureException;
	//if reqd public ShowSeat updateShowSeat(ShowSeat showSeat) throws ResponseFailureException;
	
	//seat
	public Seat addSeat(Seat seat) throws ResponseFailureException;
	public void deleteSeat(long id) throws ResponseFailureException;
	public ArrayList<Seat> getSeats() throws ResponseFailureException;
	public Seat updateSeat(Seat seat) throws ResponseFailureException;
	
	//customer
	public ArrayList<Customer> getCustomers() throws ResponseFailureException;
	public void deleteCustomer(long id) throws ResponseFailureException;
	
	//category
	public Category addCategory(Category category) throws ResponseFailureException;
	public void deleteCategory(long id) throws ResponseFailureException;
	public Category updateCategory(Category category) throws ResponseFailureException;
	
	//extras
	public Extra addExtra(Extra extra) throws ResponseFailureException;
	public void deleteExtra(long id) throws ResponseFailureException;
	public Extra updateExtra(Extra extra) throws ResponseFailureException;
	
	//ticket
	public ArrayList<Ticket> getTickets(Long msId) throws ResponseFailureException;
}
