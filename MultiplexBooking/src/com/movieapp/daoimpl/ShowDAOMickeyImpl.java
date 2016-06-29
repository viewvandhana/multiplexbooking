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
import com.adventnet.moviebooking.CUSTOMER;
import com.adventnet.moviebooking.SHOWDETAIL;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Show;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;

public class ShowDAOMickeyImpl extends MickeyBaseDAO<Show> {

	RowAdapter<Show> rowAdapter=new RowAdapter<Show>() {

		@Override
		public Row asRow(Show data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(SHOWDETAIL.SHOW_NAME,data.getShowName());
			row.set(SHOWDETAIL.SHOW_START_TIME,data.getStartTime());
			row.set(SHOWDETAIL.SHOW_END_TIME,data.getEndTime());
	        return row;
		}

		@Override
		public Show asBean(Row row) {
			// TODO Auto-generated method stub
			 Show show=new Show((Long)row.get(SHOWDETAIL.SHOW_ID),(String)row.get(SHOWDETAIL.SHOW_NAME),(String)row.get(SHOWDETAIL.SHOW_START_TIME),(String)row.get(SHOWDETAIL.SHOW_END_TIME));
				 
			 return show;
      }
	
	};
	
	@Override
	public RowAdapter<Show> getAdapter() {
		
		return rowAdapter;
		
	}

	
	public Show updateShowById(Show obj) throws DataAccessException,ResponseFailureException
	{
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),getPrimaryKeyColumnName()), obj.getId() ,QueryConstants.EQUAL);  	
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(SHOWDETAIL.SHOW_NAME,obj.getShowName());
		updateQuery.setUpdateColumn(SHOWDETAIL.SHOW_START_TIME,obj.getStartTime());
		updateQuery.setUpdateColumn(SHOWDETAIL.SHOW_END_TIME,obj.getEndTime());
		return update(updateQuery, criteria);
		
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return SHOWDETAIL.TABLE;
	}

	
	
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return SHOWDETAIL.SHOW_ID;
	}
	
	
	public int checkIfShowExist(Long showId,String startTime,String endTime) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
		Criteria criteria=new Criteria(new Column(getTableName(),SHOWDETAIL.SHOW_START_TIME),startTime,QueryConstants.EQUAL);
		criteria=criteria.and(new Criteria(new Column(getTableName(),SHOWDETAIL.SHOW_END_TIME),endTime,QueryConstants.EQUAL));
	    if(showId!=null)
	    {
		criteria=criteria.and(new Criteria(new Column(getTableName(),SHOWDETAIL.SHOW_ID),showId,QueryConstants.NOT_EQUAL));
	    }
		selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
	    ArrayList<Show> showList=getRows(selectQuery);
	   	return showList.size();
	   
	}
	
}
