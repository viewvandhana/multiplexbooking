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
import com.adventnet.moviebooking.EXTRA;
import com.adventnet.moviebooking.SHOWDETAIL;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Extra;
import com.movieapp.beans.Show;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;

public class ExtraDAOMickeyimpl extends MickeyBaseDAO<Extra>{

	RowAdapter<Extra> rowAdapter=new RowAdapter<Extra>() {

		@Override
		public Row asRow(Extra data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(EXTRA.EXTRA_NAME,data.getName());
			row.set(EXTRA.COST,data.getCost());
		    
	        return row;
		}

		@Override
		public Extra asBean(Row row) {
			// TODO Auto-generated method stub
			 Extra extra=new Extra((Long)row.get(EXTRA.EXTRA_ID),(String)row.get(EXTRA.EXTRA_NAME),(Float)row.get(EXTRA.COST));
			 return extra;
      }
	
	};
	
	@Override
	public RowAdapter<Extra> getAdapter() {
		
		return rowAdapter;
		
	}

	
	public Extra updateExtraById(Extra obj) throws DataAccessException,ResponseFailureException
	{
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),getPrimaryKeyColumnName()), obj.getId() ,QueryConstants.EQUAL);  	
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(EXTRA.EXTRA_NAME,obj.getName());
		updateQuery.setUpdateColumn(EXTRA.COST,obj.getCost());
		
		return update(updateQuery, criteria);
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return EXTRA.TABLE;
	}
	
	
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return EXTRA.EXTRA_ID;
	}
	
	public int checkIfExtraExist(Long extraId,String extraName) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
		Criteria criteria=new Criteria(new Column(getTableName(),EXTRA.EXTRA_NAME),extraName,QueryConstants.EQUAL);
		if(extraId!=null)
		{
			criteria=new Criteria(new Column(getTableName(),EXTRA.EXTRA_ID),extraId,QueryConstants.NOT_EQUAL);
			
		}
		selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
	    ArrayList<Extra> extraList=getRows(selectQuery);
	   	return extraList.size();
	   
	}	
}
