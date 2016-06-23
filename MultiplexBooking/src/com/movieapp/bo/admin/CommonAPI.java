package com.movieapp.bo.admin;

import java.util.ArrayList;

import Exception.ResponseFailureException;

import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Extra;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Show;
import com.movieapp.daoimpl.JoinDAO;
import com.movieapp.daoimpl.MovieDAOMickeyImpl;
import com.movieapp.daoimpl.MovieShowDAOMickeyImpl;
import com.movieapp.daoimpl.ScreenDAOMickeyImpl;
import com.movieapp.daoimpl.ShowSeatDAOMickeyImpl;

public class CommonAPI implements CommonAPIInterface{

	@Override
	public ArrayList<Show> getShows(String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getShowService().getRowsOnSelectionCols(null, fields);
	}

	@Override
	public String getScreens() throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).getAllScreens();
	}

	@Override
	public String getScreenForId(Long screenId) throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).getScreenForId(screenId);
		
	}

	@Override
	public ArrayList<Movie> getMovies(String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((MovieDAOMickeyImpl)ServiceInstance.getMovieService()).getRowsOnSelectionCols(null, fields);
		
	}

	@Override
	public Movie getMovieById(Long movieId, String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getMovieService().getById(movieId,fields);

	}

	@Override
	public ArrayList<MovieShow> getMovieShows() throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getMovieShowService().getRows(null);
	}

	@Override
	public String getMovieShowsByMovieShowId(Long msId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).getMovieShowById(msId);
	}

	@Override
	public String getMovieShowsByFilters(String date, Long movieId,
			Long screenId) throws ResponseFailureException {
		// TODO Auto-generated method stub
        return ((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).getMovieShowsByFilters(date,movieId,screenId);

	}
	
	@Override
	public Customer getCustomerById(Long id, String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getCustomerService().getById(id, fields);
	}
	

	@Override
	public ArrayList<Category> getCategories(String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getCategoryService().getRowsOnSelectionCols(null, fields);
	}

	@Override
	public Category getCategoryById(Long id, String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getCategoryService().getById(id, fields);
	}

	@Override
	public ArrayList<Extra> getExtras(String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getExtraService().getRowsOnSelectionCols(null, fields);
	}

	@Override
	public Extra getExtraById(Long id, String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getExtraService().getById(id, fields);
		
	}

	@Override
	public String getShowSeats(Long msId) throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).getCompleteShowSeatsByMovieShowId(msId);
	}

	@Override
	public String getTicketDetail(Long ticketId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return JoinDAO.getTicketDetail(ticketId).get("finalResponse");
	}

	@Override
	public Show getShowsById(Long id, String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		
		return ServiceInstance.getShowService().getById(id, fields);
	}

}
