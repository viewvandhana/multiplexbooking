package com.movieapp.daofactory;

import com.movieapp.beans.Seat;
import com.movieapp.daoimpl.SeatDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class SeatDAOFactory {

	public static BaseDAO getSeatDAOInstance(String type) { 
	       BaseDAO<Seat> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new SeatDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }

}
