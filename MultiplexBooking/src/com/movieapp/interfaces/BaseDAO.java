package com.movieapp.interfaces;

import java.util.ArrayList;
import java.util.Iterator;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;

public interface BaseDAO<T> {

	public RowAdapter<T> getAdapter(); 
	public T insert(T row) throws DataAccessException,ResponseFailureException;
	public ArrayList<T> insertMultipleRows(ArrayList<T> row) throws DataAccessException,ResponseFailureException;
	public void delete(Criteria criteria) throws DataAccessException,ResponseFailureException;
	public T update(UpdateQuery updateQuery,Criteria criteria) throws DataAccessException,ResponseFailureException;
	public ArrayList<T> getRows(SelectQuery query) throws ResponseFailureException;
	public void deleteById(long id) throws DataAccessException,ResponseFailureException;
	public String getTableName();
    public String getPrimaryKeyColumnName();
    public ArrayList<T> getBeans(Iterator<?> rows);  
    public ArrayList<T> getRowsOnSelectionCols(SelectQuery selectQuery,String fields) throws ResponseFailureException;
    public DataObject getDataObject(SelectQuery selectQuery) throws ResponseFailureException; 
    public T getById(Long id,String fields) throws ResponseFailureException; 
    public T updateDataObjectRow(DataObject dObj,Row row) throws DataAccessException,ResponseFailureException; 
     
	
}
