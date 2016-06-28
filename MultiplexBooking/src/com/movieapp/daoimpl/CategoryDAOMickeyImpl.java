package com.movieapp.daoimpl;

import java.util.ArrayList;
import java.util.Date;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.moviebooking.CATEGORY;
import com.adventnet.moviebooking.MOVIESHOW;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Category;
import com.movieapp.beans.MovieShow;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;


public class CategoryDAOMickeyImpl extends MickeyBaseDAO<Category> {
	RowAdapter<Category> rowAdapter=new RowAdapter<Category>() {

		@Override
		public Row asRow(Category data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(CATEGORY.CATEGORY_NAME,data.getCategoryName());
			row.set(CATEGORY.CATEGORY_FARE,data.getFare());
	        return row;
		}

		@Override
		public Category asBean(Row row) {
			// TODO Auto-generated method stub
			 Category category=new Category((Long)row.get(CATEGORY.CATEGORY_ID),(String)row.get(CATEGORY.CATEGORY_NAME),(Float)row.get(CATEGORY.CATEGORY_FARE));
			 return category;
      }
	
	};
	
	@Override
	public RowAdapter<Category> getAdapter() {
		
		return rowAdapter;
		
	}

	
	public Category updateCategoryById(Category obj) throws DataAccessException,ResponseFailureException
	{
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),CATEGORY.CATEGORY_ID), obj.getId() ,QueryConstants.EQUAL);  	
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(CATEGORY.CATEGORY_NAME,obj.getCategoryName());
		updateQuery.setUpdateColumn(CATEGORY.CATEGORY_FARE,obj.getFare());
		return update(updateQuery, criteria);
		
	
	}
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return CATEGORY.TABLE;
	}

	
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return CATEGORY.CATEGORY_ID;
	}
	
	public int checkIfCategoryExist(String categoryName) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
		Criteria criteria=new Criteria(new Column(getTableName(),CATEGORY.CATEGORY_NAME),categoryName,QueryConstants.EQUAL);
		selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
	    ArrayList<Category> categoryList=getRows(selectQuery);
	   	return categoryList.size();
	   
	}
}
