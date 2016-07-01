package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Screen;

@JsonInclude(Include.NON_NULL)
public class ScreenSeatResponseJSONWrapper {

	
	  private Long id;
	  private String screenName;
	  private int screenRows;
	  private int screenColumns;
	  private ArrayList<Long> seats;
	  
	 
	  public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setScreenRows(int screenRows) {
		this.screenRows = screenRows;
	}
	public int getScreenColumns() {
		return screenColumns;
	}
	public void setScreenColumns(int screenColumns) {
		this.screenColumns = screenColumns;
	}
	public ArrayList<Long> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Long> seats) {
		this.seats = seats;
	}
	
	public Screen constructScreenObject()
	{
		Screen screen=new Screen();
		screen.setScreenName(screenName);
		screen.setScreenRows(screenRows);
		screen.setScreenColumns(screenColumns);
		return screen;
		
	}
	public void setScreenSeats(Screen screen,ArrayList<Long> seats)
	{
		setId(screen.getId());
		setSeats(seats);
		setScreenName(screen.getScreenName());
		setScreenRows(screen.getScreenRows());
		setScreenColumns(screen.getScreenColumns());
		
		
	}
	
	  
	
}

