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
import com.adventnet.moviebooking.SCREEN;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Screen;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;
import com.movieapp.wrapperbeans.ScreenSeatResponseWrapper;

public class ScreenDAOMickeyImpl extends MickeyBaseDAO<Screen>{

	RowAdapter<Screen> rowAdapter=new RowAdapter<Screen>() {

		@Override
		public Row asRow(Screen data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(SCREEN.SCREEN_NAME,data.getScreenName());
			row.set(SCREEN.SCREEN_ROWS,data.getScreenRows());
			row.set(SCREEN.SCREEN_COLS,data.getScreenColumns());
	        return row;
		}

		@Override
		public Screen asBean(Row row) {
			// TODO Auto-generated method stub
			 Screen screen=new Screen((Long)row.get(SCREEN.SCREEN_ID),(String)row.get(SCREEN.SCREEN_NAME),(Integer)row.get(SCREEN.SCREEN_ROWS),(Integer)row.get(SCREEN.SCREEN_COLS));
			 return screen;
      }
	
	};
	
	@Override
	public RowAdapter<Screen> getAdapter() {
		
		return rowAdapter;
		
	}

	
	public Screen updateScreenById(Screen obj) throws DataAccessException,ResponseFailureException
	{
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),getPrimaryKeyColumnName()), obj.getId() ,QueryConstants.EQUAL);  	
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(SCREEN.SCREEN_NAME,obj.getScreenName());
		updateQuery.setUpdateColumn(SCREEN.SCREEN_ROWS,obj.getScreenRows());
		updateQuery.setUpdateColumn(SCREEN.SCREEN_COLS,obj.getScreenColumns());
		return update(updateQuery, criteria);
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return SCREEN.TABLE;
	}


	
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return SCREEN.SCREEN_ID;
	}
	
	public ScreenSeatResponseWrapper getAllScreens() throws ResponseFailureException
	{
		return JoinDAO.getScreensOnJoinCriteria(null);
	}

	public ScreenSeatResponseWrapper getScreenForId(Long screenId) throws ResponseFailureException
	{
	    Criteria criteria=new Criteria(new Column(SCREEN.TABLE,SCREEN.SCREEN_ID),screenId,QueryConstants.EQUAL);
		return JoinDAO.getScreensOnJoinCriteria(criteria);
	}
	
	public int checkIfScreenExist(String screenName) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
		Criteria criteria=new Criteria(new Column(getTableName(),SCREEN.SCREEN_NAME),screenName,QueryConstants.EQUAL);
		selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
	    ArrayList<Screen> screenList=getRows(selectQuery);
	   	return screenList.size();
	   
	}
	
	
}

	

