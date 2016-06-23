package com.movieapp.daoimpl;

import java.util.ArrayList;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.moviebooking.MOVIESHOW;
import com.adventnet.moviebooking.SCREEN;
import com.adventnet.moviebooking.SEAT;
import com.adventnet.moviebooking.SHOWDETAIL;
import com.adventnet.moviebooking.SHOWSEAT;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Seat;
import com.movieapp.beans.ShowSeat;
import com.movieapp.daofactory.CategoryDAOFactory;
import com.movieapp.daofactory.MovieDAOFactoy;
import com.movieapp.daofactory.MovieShowDAOFactory;
import com.movieapp.daofactory.ScreenDAOFactory;
import com.movieapp.daofactory.SeatDAOFactory;
import com.movieapp.daofactory.ShowDAOFactory;
import com.movieapp.daofactory.ShowSeatDAOFactory;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;
import com.movieapp.util.ObjectMapperUtil;

public class ShowSeatDAOMickeyImpl  extends MickeyBaseDAO<ShowSeat>{
	RowAdapter<ShowSeat> rowAdapter=new RowAdapter<ShowSeat>() {

		@Override
		public Row asRow(ShowSeat data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(SHOWSEAT.TICKET_ID,null);
			row.set(SHOWSEAT.MOVIE_SHOW_ID,data.getMovieShowID());
			row.set(SHOWSEAT.SEAT_ID,data.getSeatID());
	        return row;
		}

		@Override
		public ShowSeat asBean(Row row) {
			// TODO Auto-generated method stub
			 ShowSeat showSeat=new ShowSeat((Long)row.get(SHOWSEAT.SHOW_SEAT_ID),(Long)row.get(SHOWSEAT.TICKET_ID),(Long)row.get(SHOWSEAT.SEAT_ID),(Long)row.get(SHOWSEAT.MOVIE_SHOW_ID),(Long)row.get(SHOWSEAT.TICKET_ID)==null?true:false);
			 return showSeat;
      }
	
	};
	
	@Override
	public RowAdapter<ShowSeat> getAdapter() {
		
		return rowAdapter;
		
	}

	
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return SHOWSEAT.TABLE;
	}

	
	
	public void deleteShowSeatsByMovieShowsId(Long msId) throws DataAccessException,ResponseFailureException
	{
		
		Criteria criteria=new Criteria(new Column(getTableName(),SHOWSEAT.MOVIE_SHOW_ID),msId,QueryConstants.EQUAL);
	    delete(criteria);
		
	}
	
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return SHOWSEAT.SHOW_SEAT_ID;
	}
	
	public void updateShowSeatByMovieShowId(Long msId,Long ticketId,Long showSeatId) throws DataAccessException,ResponseFailureException
	{
		
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),getPrimaryKeyColumnName()), showSeatId ,QueryConstants.EQUAL);  	
		criteria=criteria.and(new Criteria(Column.getColumn(getTableName(),SHOWSEAT.MOVIE_SHOW_ID), msId ,QueryConstants.EQUAL));
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(SHOWSEAT.TICKET_ID,ticketId);
		update(updateQuery,criteria);
		
	
	}
	
	public void deleteShowSeatBySeatId(Long seatId) throws DataAccessException,ResponseFailureException
	{
		Criteria criteria=new Criteria(new Column(getTableName(),SHOWSEAT.SEAT_ID),seatId,QueryConstants.EQUAL);
	    delete(criteria);
		
	
	}
	
	public boolean isTicketBookedForMovieShowId(Long msId) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
	    Criteria criteria=new Criteria(new Column(getTableName(),SHOWSEAT.TICKET_ID),null,QueryConstants.NOT_EQUAL);
	    criteria=criteria.and(new Criteria(new Column(getTableName(),SHOWSEAT.MOVIE_SHOW_ID),msId,QueryConstants.EQUAL));
		selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE,SHOWSEAT.SHOW_SEAT_ID));
		selectQuery.setCriteria(criteria);
		if(getRows(selectQuery).size()>0)
		{
			return true;
		}
		return false;
	   
		
	}
	public int getAvailableSeatForMovieShow(Long msId) throws ResponseFailureException
	{
		
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
	    Criteria criteria=new Criteria(new Column(getTableName(),SHOWSEAT.TICKET_ID),null,QueryConstants.EQUAL);
	    criteria=criteria.and(new Criteria(new Column(getTableName(),SHOWSEAT.MOVIE_SHOW_ID),msId,QueryConstants.EQUAL));
		selectQuery.addSelectColumn(new Column(getTableName(),SHOWSEAT.SHOW_SEAT_ID));
		selectQuery.setCriteria(criteria);
		return getRows(selectQuery).size();
		
		
		
	}
	
	public boolean isTicketBookedForAnySeat(ArrayList<Seat> seats) throws ResponseFailureException
	{
		ArrayList<Long> seatIds=new ArrayList<Long>();
		for(int i=0;i<seats.size();i++)
		{
			seatIds.add(seats.get(i).getId());
		}
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
	    Criteria criteria=new Criteria(new Column(getTableName(),SHOWSEAT.TICKET_ID),null,QueryConstants.NOT_EQUAL);
	    criteria=criteria.and(new Criteria(new Column(getTableName(),SHOWSEAT.SEAT_ID),seatIds.toArray(),QueryConstants.IN));
	    selectQuery.addSelectColumn(new Column(getTableName(),SHOWSEAT.SHOW_SEAT_ID));
	    selectQuery.setCriteria(criteria);
		if(getRows(selectQuery).size()>0)
		{
			return true;
		}
		return false;
	   
		
	}
	
	public String getShowSeatsByTicketId(Long ticketId) throws ResponseFailureException
	{
		Criteria criteria=new Criteria(new Column(SHOWSEAT.TABLE,SHOWSEAT.TICKET_ID),ticketId,QueryConstants.EQUAL);
		return JoinDAO.getShowSeatsOnJoinCriteria(criteria);
	}
	public String getCompleteShowSeatsByMovieShowId(Long msId) throws ResponseFailureException
	{
		Criteria criteria=new Criteria(new Column(SHOWSEAT.TABLE,SHOWSEAT.MOVIE_SHOW_ID),msId,QueryConstants.EQUAL);
	    return JoinDAO.getShowSeatsOnJoinCriteria(criteria);
	}
}

