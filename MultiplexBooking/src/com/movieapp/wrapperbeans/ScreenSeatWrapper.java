package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;

@JsonInclude(Include.NON_NULL)
public class ScreenSeatWrapper {

	
	  private Long id;
	  private String screenName;
	  private int screenRows;
	  private int screenColumns;
	  private ArrayList<Seat> seats;
	  
	 
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
	public ArrayList<Seat> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
	
	public Screen getScreenObject()
	{
		Screen screen=new Screen();
		screen.setScreenName(screenName);
		screen.setScreenRows(screenRows);
		screen.setScreenColumns(screenColumns);
		return screen;
		
	}
	public void setScreenSeats(Screen screen,ArrayList<Seat> seats)
	{
		setId(screen.getId());
		setSeats(seats);
		setScreenName(screenName);
		setScreenRows(screenRows);
		setScreenColumns(screenColumns);
		
		
	}
	
	  
	
}
