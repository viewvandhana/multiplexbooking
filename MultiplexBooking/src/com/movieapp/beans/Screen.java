package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class Screen {

	
	  private Long id;
	  private String screenName;
	  private int screenRows;
	  private int screenColumns;
	  
	  
		  
	public Screen()
	{
		super();
	}
	  
	public Screen(Long id,String screenName,int rows,int cols)
	{
		this.screenName=screenName;
		this.screenRows=rows;
		this.screenColumns=cols;
		this.id=id;
	}
	  
    public Long getId() {
		return id;
	}
	public void setId(Long screenId) {
		this.id = screenId;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public int getScreenRows() {
		return screenRows;
	}
	public void setScreenRows(int rows) {
		this.screenRows = rows;
	}
	public int getScreenColumns() {
		return screenColumns;
	}
	public void setScreenColumns(int cols) {
		this.screenColumns = cols;
	}


}
