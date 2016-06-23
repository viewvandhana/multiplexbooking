package com.movieapp.bo.admin;

import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Extra;
import com.movieapp.beans.Movie;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Show;
import com.movieapp.beans.ShowSeat;
import com.movieapp.beans.Ticket;
import com.movieapp.beans.TicketCharge;
import com.movieapp.daofactory.CategoryDAOFactory;
import com.movieapp.daofactory.CustomerDAOFactory;
import com.movieapp.daofactory.ExtraDAOFactory;
import com.movieapp.daofactory.MovieDAOFactoy;
import com.movieapp.daofactory.MovieShowDAOFactory;
import com.movieapp.daofactory.ScreenDAOFactory;
import com.movieapp.daofactory.SeatDAOFactory;
import com.movieapp.daofactory.ShowDAOFactory;
import com.movieapp.daofactory.ShowSeatDAOFactory;
import com.movieapp.daofactory.TicketChargeDAOFactory;
import com.movieapp.daofactory.TicketDAOFactory;
import com.movieapp.interfaces.BaseDAO;

public class ServiceInstance {
	
	
	public static BaseDAO<Show> getShowService() {
		return ShowDAOFactory.getShowDAOInstance("mickey");
	}
	
	public static BaseDAO<ShowSeat> getShowSeatService() {
		return ShowSeatDAOFactory.getShowSeatDAOInstance("mickey");
	}

	public static BaseDAO<Screen> getScreenService() {
		return ScreenDAOFactory.getScreenDAOInstance("mickey");
	}
	
	public static BaseDAO<Seat> getSeatService() {
		return SeatDAOFactory.getSeatDAOInstance("mickey");
	}
	
	public static BaseDAO<Extra> getExtraService() {
		return ExtraDAOFactory.getExtraDAOInstance("mickey");
	}
	
	public static BaseDAO<Ticket> getTicketService() {
		return TicketDAOFactory.getTicketDAOInstance("mickey");
	}

	public static BaseDAO<Movie> getMovieService() {
		return MovieDAOFactoy.getMovieDAOInstance("mickey");
	}
	
	public static BaseDAO getMovieShowService() {
		return MovieShowDAOFactory.getMovieShowDAOInstance("mickey");
	}
	
	public static BaseDAO<Customer> getCustomerService() {
		return CustomerDAOFactory.getCustomerDAOInstance("mickey");
	}

	public static BaseDAO<Category> getCategoryService() {
		return CategoryDAOFactory.getCategoryDAOInstance("mickey");
	}
	
	public static BaseDAO<TicketCharge> getTicketChargeService() {
		return TicketChargeDAOFactory.getTicketChargeDAOInstance("mickey");
	}	
	
}

