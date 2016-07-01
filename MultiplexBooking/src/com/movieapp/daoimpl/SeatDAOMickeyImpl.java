package com.movieapp.daoimpl;

import java.util.ArrayList;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.moviebooking.SEAT;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Seat;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;
import com.movieapp.wrapperbeans.SeatResponseWrapper;

public class SeatDAOMickeyImpl extends MickeyBaseDAO<Seat>{
	RowAdapter<Seat> rowAdapter=new RowAdapter<Seat>() {

		@Override
		public Row asRow(Seat data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(SEAT.NAME,data.getName());
			row.set(SEAT.SCREEN_ID,data.getScreenID());
			row.set(SEAT.CATEGORY_ID,data.getCategoryID());
			row.set(SEAT.ROW_NO,data.getRowNumber());
			row.set(SEAT.COL_NO,data.getColumnNumber());
			row.set(SEAT.STATUS,data.getStatus());
		    
			return row;
		}

		@Override
		public Seat asBean(Row row) {
			// TODO Auto-generated method stub
			 Seat seat=new Seat((Long)row.get(SEAT.SEAT_ID),(Long)row.get(SEAT.SCREEN_ID),(Long)row.get(SEAT.CATEGORY_ID),(String)row.get(SEAT.NAME),(Integer)row.get(SEAT.ROW_NO),(Integer)row.get(SEAT.COL_NO),(Boolean)row.get(SEAT.STATUS));
			 return seat;
      }
	
	};
	
	@Override
	public RowAdapter<Seat> getAdapter() {
		
		return rowAdapter;
		
	}

	
	public void deleteSeatByScreenId(Long id) throws DataAccessException,ResponseFailureException
	{
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),SEAT.SCREEN_ID), id ,QueryConstants.EQUAL);  	
		delete(criteria);
	}
	
	
	
	public Seat updateSeatById(Seat obj) throws DataAccessException,ResponseFailureException
	{
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),getPrimaryKeyColumnName()), obj.getId() ,QueryConstants.EQUAL);  	
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(SEAT.NAME,obj.getName());
		updateQuery.setUpdateColumn(SEAT.ROW_NO,obj.getRowNumber());
		updateQuery.setUpdateColumn(SEAT.COL_NO,obj.getColumnNumber());
		updateQuery.setUpdateColumn(SEAT.SCREEN_ID,obj.getScreenID());
		updateQuery.setUpdateColumn(SEAT.CATEGORY_ID,obj.getCategoryID());
		updateQuery.setUpdateColumn(SEAT.STATUS,obj.getStatus());
		
		return update(updateQuery, criteria);
	
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return SEAT.TABLE;
	}

	
	
	public ArrayList<Seat> getSeatsByScreenId(Long screenId) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
	    Criteria criteria=new Criteria(new Column(getTableName(),SEAT.SCREEN_ID),screenId,QueryConstants.EQUAL);
	    selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
	   return getRows(selectQuery); 
	
	}
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return SEAT.SEAT_ID;
	}
	
	public boolean isSeatForScreenAvailable(int row,int col,Long screenID) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
	    Criteria criteria=new Criteria(new Column(getTableName(),SEAT.SCREEN_ID),screenID,QueryConstants.EQUAL);
	     criteria=criteria.and(new Criteria(new Column(getTableName(),SEAT. COL_NO),col,QueryConstants.EQUAL));
	     criteria=criteria.and(new Criteria(new Column(getTableName(),SEAT. ROW_NO),row,QueryConstants.EQUAL));
		    
	    selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
	   if( getRows(selectQuery).size()>0)
	   {
		   return true;
	   }
	   else
	   {
		   return false;
	   }
	
		
	}
	
	public SeatResponseWrapper getSeatById(Long seatId) throws ResponseFailureException
	{
		
		 Criteria criteria=new Criteria(new Column(getTableName(),SEAT.SEAT_ID),seatId,QueryConstants.EQUAL);
		 return JoinDAO.getSeatOnJoinCriteria(criteria);  
	}

}


	

