package com.movieapp.daofactory;

import com.movieapp.beans.Extra;
import com.movieapp.daoimpl.ExtraDAOMickeyimpl;
import com.movieapp.interfaces.BaseDAO;

public class ExtraDAOFactory {

	public static BaseDAO getExtraDAOInstance(String type) { 
	       BaseDAO<Extra> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new ExtraDAOMickeyimpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }

	
}
