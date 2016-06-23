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
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;
import com.movieapp.util.ObjectMapperUtil;

public class CustomerDAOMickeyImpl extends MickeyBaseDAO<Customer> {
	RowAdapter<Customer> rowAdapter=new RowAdapter<Customer>() {

		@Override
		public Row asRow(Customer data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(CUSTOMER.CUSTOMER_NAME,data.getName());
			row.set(CUSTOMER.CUSTOMER_EMAIL,data.getEmail());
			row.set(CUSTOMER.CUSTOMER_PHONE,data.getPhoneNumber());
	        return row;
		}

		@Override
		public Customer asBean(Row row) {
			// TODO Auto-generated method stub
			 Customer customer=new Customer((Long)row.get(CUSTOMER.CUSTOMER_ID),(String)row.get(CUSTOMER.CUSTOMER_NAME),(String)row.get(CUSTOMER.CUSTOMER_EMAIL),(String)row.get(CUSTOMER.CUSTOMER_PHONE));
			 return customer;
      }
	
	};
	
	@Override
	public RowAdapter<Customer> getAdapter() {
		
		return rowAdapter;
		
	}

	
	public Customer updateCustomerById(Customer obj) throws DataAccessException,ResponseFailureException
	{
		UpdateQuery updateQuery = new UpdateQueryImpl(getTableName());
		Criteria criteria=new Criteria(Column.getColumn(getTableName(),CUSTOMER.CUSTOMER_ID), obj.getId() ,QueryConstants.EQUAL);  	
		updateQuery.setCriteria(criteria);
		updateQuery.setUpdateColumn(CUSTOMER.CUSTOMER_NAME,obj.getName());
		updateQuery.setUpdateColumn(CUSTOMER.CUSTOMER_EMAIL,obj.getEmail());
		updateQuery.setUpdateColumn(CUSTOMER.CUSTOMER_PHONE,obj.getPhoneNumber());
		
		return update(updateQuery, criteria);
	
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return CUSTOMER.TABLE;
	}

	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return CUSTOMER.CUSTOMER_ID;
	}
	
	public Customer getCustomer(String email) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(getTableName())); 
		Criteria criteria=new Criteria(new Column(getTableName(),CUSTOMER.CUSTOMER_EMAIL),email,QueryConstants.EQUAL);
		selectQuery.addSelectColumn(new Column(null,"*"));
		selectQuery.setCriteria(criteria);
	    ArrayList<Customer> customerList=getRows(selectQuery);
	    if(customerList.size()>0)
	    {
	    	return customerList.get(0);
	    }
		return null;
	   
	}
	
	
	public String checkCustomer(String mailId) throws ResponseFailureException
	{
		Customer customer=getCustomer(mailId);
		if(customer!=null)
		{
			return ObjectMapperUtil.getCustomMappedString("customer",customer);
		}
		else
		{
			throw new ResponseFailureException("No such customer");
		}
		
	}
}
