package com.movieapp.daofactory;

import com.movieapp.beans.Show;
import com.movieapp.daoimpl.ShowDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class ShowDAOFactory {

	public static BaseDAO getShowDAOInstance(String type) { 
       BaseDAO<Show> baseDAO;
		if (type.equalsIgnoreCase("mickey")) {
            baseDAO=new ShowDAOMickeyImpl();
            return baseDAO;
        } 
        
		return null;
    }
	
}
