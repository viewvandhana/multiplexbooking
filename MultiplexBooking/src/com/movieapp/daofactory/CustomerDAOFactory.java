package com.movieapp.daofactory;

import com.movieapp.beans.Customer;
import com.movieapp.daoimpl.CustomerDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class CustomerDAOFactory {
	
	public static BaseDAO getCustomerDAOInstance(String type) { 
	       BaseDAO<Customer> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new CustomerDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }

}
