package com.movieapp.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.moviebooking.TICKET;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Ticket;
import com.movieapp.beans.TicketCharge;
import com.movieapp.daofactory.CategoryDAOFactory;
import com.movieapp.daofactory.CustomerDAOFactory;
import com.movieapp.daofactory.ExtraDAOFactory;
import com.movieapp.daofactory.MovieDAOFactoy;
import com.movieapp.daofactory.MovieShowDAOFactory;
import com.movieapp.daofactory.ScreenDAOFactory;
import com.movieapp.daofactory.SeatDAOFactory;
import com.movieapp.daofactory.ShowDAOFactory;
import com.movieapp.daofactory.TicketChargeDAOFactory;
import com.movieapp.daofactory.TicketDAOFactory;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;
import com.movieapp.util.ObjectMapperUtil;

public class TicketDAOMickeyImpl extends MickeyBaseDAO<Ticket>{

	RowAdapter<Ticket> rowAdapter=new RowAdapter<Ticket>() {

		@Override
		public Row asRow(Ticket data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(TICKET.MOVIE_SHOW_ID,data.getMovieShowID());
			row.set(TICKET.CUSTOMER_ID,data.getCustomerID());
			row.set(TICKET.TOTAL_COST,data.getTotalCost());
	        
			return row;
		}

		@Override
		public Ticket asBean(Row row) {
			// TODO Auto-generated method stub
			 Ticket ticket=new Ticket((Long)row.get(TICKET.TICKET_ID),(Long)row.get(TICKET.MOVIE_SHOW_ID),(Long)row.get(TICKET.CUSTOMER_ID),(Float)row.get(TICKET.TOTAL_COST));
			 return ticket;
      }
	
	};
	
	@Override
	public RowAdapter<Ticket> getAdapter() {
		
		return rowAdapter;
		
	}

	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TICKET.TABLE;
	}

	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return TICKET.TICKET_ID;
	}
	
	public ArrayList<Ticket> getTicketsByMovieShowId(Long msId) throws ResponseFailureException
	{
		
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
	    selectQuery.addSelectColumn(new Column(getTableName(),"*"));
	    Criteria criteria=new Criteria(new Column(getTableName(),TICKET.MOVIE_SHOW_ID),msId , QueryConstants.EQUAL);
	    selectQuery.setCriteria(criteria);		   
	    return getRows(selectQuery);
	    
		
	}
	
		
}
