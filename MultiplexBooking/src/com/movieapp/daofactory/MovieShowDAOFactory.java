package com.movieapp.daofactory;

import com.movieapp.beans.MovieShow;
import com.movieapp.daoimpl.MovieShowDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class MovieShowDAOFactory {

	public static BaseDAO getMovieShowDAOInstance(String type) { 
	       BaseDAO<MovieShow> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new MovieShowDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }
	
}
