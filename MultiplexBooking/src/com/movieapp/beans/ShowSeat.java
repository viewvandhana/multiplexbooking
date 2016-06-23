package com.movieapp.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ShowSeat {

	private Long id;
	private Long ticketID;
	private Long seatID;
	private Long movieShowID;
	private boolean isAvailable;
	
	public ShowSeat(Long id, Long ticketId, Long seatId, Long movieShowId,boolean isAvailable) {
		super();
		this.id = id;
		this.ticketID = ticketId;
		this.seatID = seatId;
		this.isAvailable=isAvailable;
		this.movieShowID = movieShowId;
	}
	
	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

    public ShowSeat()
	{
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTicketID() {
		return ticketID;
	}
	public void setTicketID(Long ticketId) {
		this.ticketID = ticketId;
	}
	public Long getSeatID() {
		return seatID;
	}
	public void setSeatID(Long seatId) {
		this.seatID = seatId;
	}
	public Long getMovieShowID() {
		return movieShowID;
	}
	public void setMovieShowID(Long movieShowId) {
		this.movieShowID = movieShowId;
	}

	
	
}
