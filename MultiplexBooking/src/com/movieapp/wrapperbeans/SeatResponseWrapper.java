package com.movieapp.wrapperbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Category;
import com.movieapp.beans.Seat;

@JsonInclude(Include.NON_NULL)
public class SeatResponseWrapper {

	private Seat seat;
	private Category category;

	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	
	
	
	
}
