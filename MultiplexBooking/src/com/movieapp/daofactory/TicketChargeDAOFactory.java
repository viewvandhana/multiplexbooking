package com.movieapp.daofactory;

import com.movieapp.beans.TicketCharge;
import com.movieapp.daoimpl.TicketChargeDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class TicketChargeDAOFactory {

	public static BaseDAO getTicketChargeDAOInstance(String type) { 
	       BaseDAO<TicketCharge> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new TicketChargeDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }
}
