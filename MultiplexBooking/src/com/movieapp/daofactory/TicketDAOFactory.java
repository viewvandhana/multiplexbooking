package com.movieapp.daofactory;

import com.movieapp.beans.Ticket;
import com.movieapp.daoimpl.TicketDAOMickeyImpl;
import com.movieapp.interfaces.BaseDAO;

public class TicketDAOFactory {
	public static BaseDAO getTicketDAOInstance(String type) { 
	       BaseDAO<Ticket> baseDAO;
			if (type.equalsIgnoreCase("mickey")) {
	            baseDAO=new TicketDAOMickeyImpl();
	            return baseDAO;
	        } 
	        
			return null;
	    }
	
}
