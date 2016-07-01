package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Category;
import com.movieapp.beans.Seat;

@JsonInclude(Include.NON_NULL)
public class ScreenSeatResponseWrapper {

	private ArrayList<ScreenSeatResponseJSONWrapper> screens;
	private ScreenSeatResponseJSONWrapper screen;
	private ArrayList<Seat> seats=new ArrayList<Seat>();
	private ArrayList<Category> categories=new ArrayList<Category>();

	public ScreenSeatResponseJSONWrapper getScreen() {
		return screen;
	}
	public void setScreen(ScreenSeatResponseJSONWrapper screen) {
		this.screen = screen;
	}
	public ArrayList<ScreenSeatResponseJSONWrapper> getScreens() {
		return screens;
	}
	public void setScreens(ArrayList<ScreenSeatResponseJSONWrapper> screens) {
		this.screens = screens;
	}
	public ArrayList<Seat> getSeats() {
		return seats;
	}
	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	
}
