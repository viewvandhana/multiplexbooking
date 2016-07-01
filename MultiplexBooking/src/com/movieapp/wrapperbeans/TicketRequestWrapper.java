package com.movieapp.wrapperbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TicketRequestWrapper {

	private TicketWrapper ticket;

	public TicketWrapper getTicket() {
		return ticket;
	}

	public void setTicket(TicketWrapper ticket) {
		this.ticket = ticket;
	}
}
