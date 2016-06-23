package com.movieapp.daofactory;

import com.movieapp.beans.Screen;
import com.movieapp.daoimpl.ScreenDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class ScreenDAOFactory {

	public static BaseDAO getScreenDAOInstance(String type) { 
	       BaseDAO<Screen> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new ScreenDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }
	
}
