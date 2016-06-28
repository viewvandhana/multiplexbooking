package com.movieapp.interfaces;

import java.util.ArrayList;
import java.util.Iterator;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;

public abstract class MickeyBaseDAO<T>  implements BaseDAO<T> {

	

	@Override
	public T insert(T row) throws DataAccessException,ResponseFailureException{
			   	
		    	try {
		    	 	Persistence persistence = (Persistence) BeanUtil.lookup("Persistence"); 
		    		DataObject dataObj = DataAccess.constructDataObject();
		    		dataObj.addRow(getAdapter().asRow(row));
		    		DataObject insertedObj=persistence.add(dataObj);
		    		Row newRow=insertedObj.getFirstRow(getTableName());
		    		return getAdapter().asBean(newRow);
		    		
				}
		    	catch(DataAccessException e)
		    	{
		    		throw e;
		    	}
		    	catch (Exception e) {
				
					throw new ResponseFailureException(e.getMessage());
				}
		  
		    }	
	
	@Override
	public ArrayList<T> insertMultipleRows(ArrayList<T> rows) throws DataAccessException,ResponseFailureException
	{
	try {
		
	 	Persistence persistence = (Persistence) BeanUtil.lookup("Persistence"); 
	 	ArrayList<T> insertedBeans=new ArrayList<>();
		DataObject dataObj = DataAccess.constructDataObject();
		for(int i=0;i<rows.size();i++)
		{
		dataObj.addRow(getAdapter().asRow(rows.get(i)));
		}
		DataObject insertedObj=persistence.add(dataObj);
		Iterator<?> rowIterator = insertedObj.getRows(getTableName());
	    while(rowIterator.hasNext())
	    {
	    	Row row=(Row)rowIterator.next();
	    	insertedBeans.add(getAdapter().asBean(row));
	    }
		return insertedBeans;
	} 
	catch(DataAccessException e)
	{
		throw e;
	}
	catch (Exception e) {
	
		throw new ResponseFailureException(e.getMessage());
	}
	}
	
	@Override
	public void delete(Criteria criteria) throws DataAccessException,ResponseFailureException {
		// TODO Auto-generated method stub
	
		try {
			Persistence p = (Persistence) BeanUtil.lookup("Persistence"); 
			p.delete(criteria);
    	 				
		} 
		catch(DataAccessException e)
		{
			throw e;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ResponseFailureException(e.getMessage());
		}
    	

		
	
	}
	@Override
	public DataObject getDataObject(SelectQuery selectQuery) throws ResponseFailureException{
		// TODO Auto-generated method stub
		    try{
		    	if(selectQuery==null)
				{
				selectQuery=new SelectQueryImpl(Table.getTable(getTableName()));
				selectQuery.addSelectColumn(new Column(null,"*"));
				
				}
			
			Persistence persist = (Persistence) BeanUtil.lookup("Persistence");
	        DataObject data = persist.get(selectQuery);
	        return data;
	        }
		    catch(Exception e)
	        {
	        	throw new ResponseFailureException(e.getMessage());
	        }
		
	}
	
	@Override
	public void deleteById(long id) throws DataAccessException,ResponseFailureException {
		// TODO Auto-generated method stub
		
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),getPrimaryKeyColumnName()), id ,QueryConstants.EQUAL);  	
		delete(criteria);
	
		
	}
	
	@Override
	public ArrayList<T> getBeans(Iterator<?> rows) {
		// TODO Auto-generated method stub
			if (rows == null) {
				return null;
			}
			
			ArrayList<T> arrayList = new ArrayList<>();
			while(rows.hasNext()){ 
	        	arrayList.add((T) getAdapter().asBean((Row)rows.next()));			 
	        } 
			return arrayList;
	
	}
	
	@Override
	public ArrayList<T> getRows(SelectQuery selectQuery) throws ResponseFailureException{
		// TODO Auto-generated method stub
		
	        ArrayList<T> arrayList=new ArrayList<T>();
		    try{
		    	
		    DataObject data = getDataObject(selectQuery);
	        Iterator<?> rows = data.getRows(getTableName());
	        arrayList=getBeans(rows);
	        return arrayList;
	        }
		    catch(Exception e)
		    {
		    	throw new ResponseFailureException(e.getMessage());
		    }
		  
	
	}
	
	
	   
	
	@Override
	public T update(UpdateQuery updateQuery,Criteria criteria) throws DataAccessException,ResponseFailureException {
		// TODO Auto-generated method stub
		
		
		
		try{
	    Persistence persup = (Persistence) BeanUtil.lookup("Persistence"); 
        persup.update(updateQuery);
        DataObject updatedObj = persup.get(getTableName(), criteria);
    	if(updatedObj.isEmpty())
    	{
    		return null;
    	}
        Row newRow=updatedObj.getFirstRow(getTableName());
		return getAdapter().asBean(newRow);

		}
		catch(DataAccessException e)
		{
			throw e;
		}
		catch(Exception excep)
		{
			throw new ResponseFailureException(excep.getMessage());
		}
        
     
	
	}
	

	
	@Override
	public ArrayList<T> getRowsOnSelectionCols(SelectQuery selectQuery,
			String fields) throws ResponseFailureException{
		// TODO Auto-generated method stub
		try{
	    	if(selectQuery==null)
			{
			selectQuery=new SelectQueryImpl(Table.getTable(getTableName()));
			
			}
	    	if(fields==null)
			{
			selectQuery.addSelectColumn(new Column(null,"*"));
			}
			else
			{
				selectQuery.addSelectColumn(new Column(getTableName(),getPrimaryKeyColumnName()));
				
				String[] cols=fields.split(",");
				for(int i=0;i<cols.length;i++)
				{
				selectQuery.addSelectColumn(new Column(getTableName(),cols[i].toUpperCase()));
				}
						
			}
		
		
		return getRows(selectQuery);
		}
        catch(Exception e)
        {
        	throw new ResponseFailureException(e.getMessage());
        }
		
		
	}
	
	@Override
	public T getById(Long id, String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		SelectQuery selectQuery = new SelectQueryImpl(
				Table.getTable(getTableName()));
	
		Criteria criteria = new Criteria(
				new Column(getTableName(), getPrimaryKeyColumnName()), id,
				QueryConstants.EQUAL);
		selectQuery.setCriteria(criteria);
		return getRowsOnSelectionCols(selectQuery, fields).get(0);
	
	}
	
	    @Override
	    public T updateDataObjectRow(DataObject dObj, Row row) throws DataAccessException,ResponseFailureException
	    		 {
	    	// TODO Auto-generated method stub
	    	dObj.updateRow(row);
	  	    return getAdapter().asBean(DataAccess.update(dObj).getFirstRow(getTableName()));
	  		
	    }
}
