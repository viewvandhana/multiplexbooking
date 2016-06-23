package com.movieapp.daoimpl;

import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.moviebooking.TICKETCHARGE;
import com.adventnet.persistence.Row;
import com.movieapp.beans.TicketCharge;
import com.movieapp.interfaces.MickeyBaseDAO;
import com.movieapp.interfaces.RowAdapter;

public class TicketChargeDAOMickeyImpl extends MickeyBaseDAO<TicketCharge> {
	RowAdapter<TicketCharge> rowAdapter=new RowAdapter<TicketCharge>() {

		@Override
		public Row asRow(TicketCharge data) {
			// TODO Auto-generated method stub
			Row row = new Row(getTableName());
			row.set(TICKETCHARGE.TICKET_ID,data.getTicketID());
			row.set(TICKETCHARGE.EXTRA_ID,data.getExtraID());
			row.set(TICKETCHARGE.QUANTITY,data.getQuantity());
	        return row;
		}

		@Override
		public TicketCharge asBean(Row row) {
			// TODO Auto-generated method stub
			 TicketCharge ticketCharge=new TicketCharge((Long)row.get(TICKETCHARGE.TICKET_CHARGE_ID),(Long)row.get(TICKETCHARGE.TICKET_ID),(Long)row.get(TICKETCHARGE.EXTRA_ID),(Integer)row.get(TICKETCHARGE.QUANTITY));
			 return ticketCharge;
      }
	
	};
	
	@Override
	public RowAdapter<TicketCharge> getAdapter() {
		
		return rowAdapter;
		
	}

	
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TICKETCHARGE.TABLE;
	}

	
	
	@Override
	public String getPrimaryKeyColumnName() {
		// TODO Auto-generated method stub
		return TICKETCHARGE.TICKET_CHARGE_ID;
	}
	
	
}

