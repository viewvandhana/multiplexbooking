package com.movieapp.wrapperbeans;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.ShowSeat;

@JsonInclude(Include.NON_NULL)
public class ShowSeatWrapper {

	private ShowSeat showseat;

	private ArrayList<ShowSeat> showseats;
	public ArrayList<ShowSeat> getShowseats() {
		return showseats;
	}

	public void setShowseats(ArrayList<ShowSeat> showseats) {
		this.showseats = showseats;
	}

	public ShowSeat getShowseat() {
		return showseat;
	}

	public void setShowseat(ShowSeat showseat) {
		this.showseat = showseat;
	}
	
	
}
