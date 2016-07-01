package com.movieapp.wrapperbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.movieapp.beans.Seat;

@JsonInclude(Include.NON_NULL)
public class SeatWrapper {

	private Seat seat;

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}
}
