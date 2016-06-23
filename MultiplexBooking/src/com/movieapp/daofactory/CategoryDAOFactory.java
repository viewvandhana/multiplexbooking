package com.movieapp.daofactory;

import com.movieapp.beans.Category;
import com.movieapp.daoimpl.CategoryDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class CategoryDAOFactory {

	public static BaseDAO getCategoryDAOInstance(String type) { 
	       BaseDAO<Category> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new CategoryDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }

	
}
