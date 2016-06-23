package com.movieapp.daofactory;

import com.movieapp.beans.ShowSeat;
import com.movieapp.daoimpl.ShowSeatDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class ShowSeatDAOFactory {

	public static BaseDAO getShowSeatDAOInstance(String type) { 
	       BaseDAO<ShowSeat> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new ShowSeatDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }
	
}
