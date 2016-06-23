package com.movieapp.daofactory;

import com.movieapp.beans.Movie;
import com.movieapp.daoimpl.MovieDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class MovieDAOFactoy {

	public static BaseDAO getMovieDAOInstance(String type) { 
	       BaseDAO<Movie> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new MovieDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }

	
}
