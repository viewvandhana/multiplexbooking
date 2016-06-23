package com.movieapp.bo.admin;

import java.util.ArrayList;

import javax.transaction.SystemException;

import Exception.ResponseFailureException;

import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.beans.Extra;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Show;
import com.movieapp.beans.ShowSeat;
import com.movieapp.beans.Ticket;
import com.movieapp.daoimpl.CategoryDAOMickeyImpl;
import com.movieapp.daoimpl.ExtraDAOMickeyimpl;
import com.movieapp.daoimpl.JoinDAO;
import com.movieapp.daoimpl.MovieDAOMickeyImpl;
import com.movieapp.daoimpl.MovieShowDAOMickeyImpl;
import com.movieapp.daoimpl.ScreenDAOMickeyImpl;
import com.movieapp.daoimpl.SeatDAOMickeyImpl;
import com.movieapp.daoimpl.ShowDAOMickeyImpl;
import com.movieapp.daoimpl.ShowSeatDAOMickeyImpl;
import com.movieapp.daoimpl.TicketDAOMickeyImpl;
import com.movieapp.util.AppUtil;
import com.movieapp.util.JSONParser;
public class AdminAPI implements AdminAPIInterface{

	@Override
	public Show addShow(Show show) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
		DataAccess.getTransactionManager().begin();
			
		Show showInserted=(Show)ServiceInstance.getShowService().insert(show);
		DataAccess.getTransactionManager().commit();
		
		return showInserted;
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
	
	}

	@Override
	public void deleteShow(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			if(!JoinDAO.isTicketBookedForShow(id))
			{
				DataAccess.getTransactionManager().begin();
				ServiceInstance.getShowService().deleteById(id);
				DataAccess.getTransactionManager().commit();
			}
			else
			{
				throw new ResponseFailureException("Ticked booked for the show.Deletion cant be performed");
			}
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
	}

	@Override
	public Show updateShow(Show show) throws ResponseFailureException
	{
		
	if(!JoinDAO.isTicketBookedForShow(show.getId()))
	{
	try{
		DataAccess.getTransactionManager().begin();
		
		Show showUpdated=(Show)((ShowDAOMickeyImpl)ServiceInstance.getShowService()).updateShowById(show);
		DataAccess.getTransactionManager().commit();
		
		return showUpdated;
	}
	catch(DataAccessException e)
	{
		try {
			DataAccess.getTransactionManager().rollback();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		throw new ResponseFailureException(e.getMessage());
	}
	catch(Exception e)
	{
		throw new ResponseFailureException(e.getMessage());
	}
	}
	else
	{
		throw new ResponseFailureException("Cannot perform this operation as tickets are already booked for this show");

	}
	
	}

	@Override
	public String addScreen(String screenObj) throws ResponseFailureException {
		// TODO Auto-generated method stub
		
		try{
		DataAccess.getTransactionManager().begin();
		
		Screen screen=JSONParser.INSTANCE.getScreenObject(screenObj);
		Screen screenInserted=(Screen)ServiceInstance.getScreenService().insert(screen);
		
		ArrayList<Seat> seatList=JSONParser.INSTANCE.getSeatsArray(screenObj);
		for(int i=0;i<seatList.size();i++)
		{
			Seat seat=seatList.get(i);
			seat.setName(AppUtil.Instance.generateSeatName(seat.getRowNumber(), seat.getColumnNumber()));
			seat.setScreenID(screenInserted.getId());
			
		}
		ServiceInstance.getSeatService().insertMultipleRows(seatList);
		DataAccess.getTransactionManager().commit();
		
		return new CommonAPI().getScreenForId(screenInserted.getId());
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
	}

	@Override
	public void deleteScreen(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			if(!JoinDAO.isTicketBookedForScreen(id))
			{
			DataAccess.getTransactionManager().begin();
		ServiceInstance.getScreenService().deleteById(id);
		DataAccess.getTransactionManager().commit();
			}
			else
			{
				throw new ResponseFailureException("Ticked booked for the screen.Deletion cant be performed");
			}
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
	}

	@Override
	public String updateScreen(String screenObj,Long screenId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			
		Screen screen=JSONParser.INSTANCE.getScreenObject(screenObj);
		screen.setId(screenId);
		
		//update screen
		Long msId=((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).isMovieShowScheduledForScreen(screen.getId());
		if(msId!=null)
		{
		if(!JoinDAO.isTicketBookedForScreen(screen.getId()))
		{
		deleteSeatsOnScreenUpdate(screen, msId);
		ArrayList<Seat> seatList=JSONParser.INSTANCE.getSeatsArray(screenObj);
		ArrayList<ShowSeat> showSeatList=new ArrayList<ShowSeat>();
		
		for(int i=0;i<seatList.size();i++)
		{
			Seat seat=seatList.get(i);
			seat.setName(AppUtil.Instance.generateSeatName(seat.getRowNumber(), seat.getColumnNumber()));
			seat.setScreenID(screen.getId());
		}
		ArrayList<Seat> seatsInserted=ServiceInstance.getSeatService().insertMultipleRows(seatList);
		for(int i=0;i<seatsInserted.size();i++)
		{
		   Seat seat=seatsInserted.get(i);
		if(seat.getStatus())
		{
			showSeatList.add(new ShowSeat(null,null, seat.getId(),msId,true));
		}
		}
		ServiceInstance.getShowSeatService().insertMultipleRows(showSeatList);
		DataAccess.getTransactionManager().commit();
		
		return new CommonAPI().getScreenForId(screen.getId());
		}
		else
		{
			throw new ResponseFailureException("Cannot perform this operation as tickets are already booked for this screen");
	
		}
		}
		else
		{
			deleteSeatsOnScreenUpdate(screen, msId);
			ArrayList<Seat> seatList=JSONParser.INSTANCE.getSeatsArray(screenObj);
			for(int i=0;i<seatList.size();i++)
			{
				Seat seat=seatList.get(i);
				seat.setName(AppUtil.Instance.generateSeatName(seat.getRowNumber(), seat.getColumnNumber()));
				seat.setScreenID(screen.getId());
				ServiceInstance.getSeatService().insert(seat);
			}
			
			DataAccess.getTransactionManager().commit();
			
			return new CommonAPI().getScreenForId(screen.getId());
			
		}
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

	}

	@Override
	public String updateScreenSeats(String screenObj,Long screenId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			
			DataAccess.getTransactionManager().begin();
			
		ArrayList<Seat> seatList=JSONParser.INSTANCE.getSeatsArray(screenObj);
		ArrayList<Seat> seatListToBeAdded=new ArrayList<Seat>();
	
		Screen screen=JSONParser.INSTANCE.getScreenObject(screenObj);
		screen.setId(screenId);
		Long msId=((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).isMovieShowScheduledForScreen(screen.getId());
		if(msId!=null)
		{
	   		
		
		ArrayList<ShowSeat> showSeatList=new ArrayList<ShowSeat>();
			if(!((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).isTicketBookedForAnySeat(seatList))
			{
				 ((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).updateScreenById(screen);
					
				for(int i=0;i<seatList.size();i++)
				{
				
			Seat seat=seatList.get(i);
			seat.setScreenID(screen.getId());
			seat.setName(AppUtil.Instance.generateSeatName(seat.getRowNumber(),seat.getColumnNumber()));
			
			if(seat.getId()!=null && !seat.getIsDeleted())
			{
			((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).updateSeatById(seat);
			if(seat.getStatus())
			{
				//insert into showseat
				showSeatList.add(new ShowSeat(null,null, seat.getId(),msId,true));
				
			}
			else
			{
				//delete frm show seat
				((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).deleteShowSeatBySeatId(seat.getId());
			}
			}
			else if(seat.getId()!=null && seat.getIsDeleted())
			{
			
	            //delete from seat
				((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).deleteById(seat.getId());
				
				
			}
			else if(seat.getId()==null)
			{
				//insert into seat
				if(!((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).isSeatForScreenAvailable(seat.getRowNumber(),seat.getColumnNumber(),screen.getId()))
				{
				
				
				seatListToBeAdded.add(seat);
				}
				else
				{
					throw new ResponseFailureException("Already seat added in this location["+seat.getRowNumber()+","+seat.getColumnNumber()+"]");
				}
				
			}
			}
				ArrayList<Seat> seatsInserted=ServiceInstance.getSeatService().insertMultipleRows(seatListToBeAdded);
				for(int i=0;i<seatsInserted.size();i++)
				{
				   Seat newSeat=seatsInserted.get(i);
				if(newSeat.getStatus())
				{
					showSeatList.add(new ShowSeat(null,null, newSeat.getId(),msId,true));
				}
				}
			
				addShowSeat(showSeatList);	
				
				DataAccess.getTransactionManager().commit();
					
			}
			else
			{
				throw new ResponseFailureException("Cannot update screen seats..Ticket already booked in the selected seat(s)");
				//throw message saying ticket booked.so seat cant be changed
			}
			
			
			
		return new CommonAPI().getScreenForId(screen.getId());
		}
		else
		{
			 ((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).updateScreenById(screen);
				
			for(int i=0;i<seatList.size();i++)
			{
				Seat seat=seatList.get(i);
				seat.setScreenID(screen.getId());
				seat.setName(AppUtil.Instance.generateSeatName(seat.getRowNumber(),seat.getColumnNumber()));
				
				if(seat.getId()!=null && !seat.getIsDeleted())
				{
				//ask this ask to send screenid and seatname
					//	seat.setName(AppUtil.Instance.generateSeatName(seat.getRowNumber(), seat.getColumnNumber()));
					//seat.setScreenID(screen.getId());
				
					
				((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).updateSeatById(seat);
				
				}
				else if(seat.getId()!=null && seat.getIsDeleted())
				{
				
		            //delete from seat
					((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).deleteById(seat.getId());
					
					
				}
				else if(seat.getId()==null)
				{
					
					//insert into seat
					if(!((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).isSeatForScreenAvailable(seat.getRowNumber(),seat.getColumnNumber(),screen.getId()))
					{
					seatListToBeAdded.add(seat);
				     }
					else
					{
						throw new ResponseFailureException("Already seat added in this location["+seat.getRowNumber()+","+seat.getColumnNumber()+"]");
					}
					
				}
				
			}
			
			ServiceInstance.getSeatService().insertMultipleRows(seatListToBeAdded);
			DataAccess.getTransactionManager().commit();
			
			
			return new CommonAPI().getScreenForId(screen.getId());
		
		}
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		

	}

	@Override
	public Movie addMovie(Movie movie)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			
		DataAccess.getTransactionManager().begin();
		
		Movie movieInserted=(Movie)ServiceInstance.getMovieService().insert(movie);
		DataAccess.getTransactionManager().commit();
		
		return movieInserted;
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

	}

	@Override
	public void deleteMovie(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
		DataAccess.getTransactionManager().begin();
		((MovieDAOMickeyImpl)ServiceInstance.getMovieService()).deleteById(id);
		DataAccess.getTransactionManager().commit();
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

		
	
		
	}

	@Override
	public Movie updateMovie(Movie movie)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			
		
		Movie movieUpdated=(Movie)((MovieDAOMickeyImpl)ServiceInstance.getMovieService()).updateMovieById(movie);
		
		DataAccess.getTransactionManager().commit();
		
		return movieUpdated; 
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

		
		


	}

	@Override
	public String addMovieShow(ArrayList<MovieShow> movieShowList)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		
		ArrayList<Long> msIds=new ArrayList<Long>();
		try{
			DataAccess.getTransactionManager().begin();
		
		for(int i=0;i<movieShowList.size();i++)
		{
				
		MovieShow movieShowInserted=(MovieShow)ServiceInstance.getMovieShowService().insert(movieShowList.get(i));
		
		msIds.add(movieShowInserted.getId());
		ArrayList<ShowSeat> showSeatList=new ArrayList<ShowSeat>();
		ArrayList<Seat> screenSeatList=((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).getSeatsByScreenId(movieShowInserted.getScreenID());
		for(int j=0;j<screenSeatList.size();j++)
		{
			Seat seat=screenSeatList.get(j);
			if(seat.getStatus())
			{
				showSeatList.add(new ShowSeat(null,null, seat.getId(),movieShowInserted.getId(),true));
			}
		}
		
		addShowSeat(showSeatList);
		}
		DataAccess.getTransactionManager().commit();
		
			}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		
		}
		
		
		
		return ((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).getMovieShowsByMovieShowIdList(msIds);
		
		
		
	}

	@Override
	public void deleteMovieShow(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			
		ServiceInstance.getMovieShowService().deleteById(id);
		DataAccess.getTransactionManager().commit();
		
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
		
	}

	@Override
	public String updateMovieShow(MovieShow movieShow)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		Long screenId=((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).getScreenIdForMovieShowId(movieShow.getId());
		
		if(!((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).isTicketBookedForMovieShowId(movieShow.getId()))
		{
			try{
				DataAccess.getTransactionManager().begin();
				
		MovieShow movieShowUpdated=(MovieShow)((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).updateMovieShowById(movieShow);
		if(!screenId.equals(movieShow.getScreenID())){
				
		((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).deleteShowSeatsByMovieShowsId(movieShow.getId());
		ArrayList<Seat> newScreenSeatList=((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).getSeatsByScreenId(movieShowUpdated.getScreenID());
		ArrayList<ShowSeat> showSeatList=new ArrayList<ShowSeat>();
		
		for(int i=0;i<newScreenSeatList.size();i++)
		{
			Seat seat=newScreenSeatList.get(i);
			if(seat.getStatus())
			{
				showSeatList.add(new ShowSeat(null,null, seat.getId(),movieShowUpdated.getId(),true));
			}
		}
		addShowSeat(showSeatList);
		
		}
		DataAccess.getTransactionManager().commit();
		
		return ((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).getMovieShowById(movieShowUpdated.getId());
			}
			catch(DataAccessException e)
			{
				try {
					DataAccess.getTransactionManager().rollback();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				throw new ResponseFailureException(e.getMessage());
			}
			catch(Exception e)
			{
				throw new ResponseFailureException(e.getMessage());
			}

			
			

		
	}	
	else
	{
		
		throw new ResponseFailureException("Cannot perform this operation as tickets are booked for this show");
		
		
	}

	}

	@Override
	public Seat addSeat(Seat seat) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		    Seat seatInserted=(Seat)ServiceInstance.getSeatService().insert(seat);
		    DataAccess.getTransactionManager().commit();
		    return seatInserted;
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
		
	}

	@Override
	public void deleteSeat(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		ServiceInstance.getSeatService().deleteById(id);
		DataAccess.getTransactionManager().commit();
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
		
	}

	@Override
	public ArrayList<Seat> getSeats() throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getSeatService().getRows(null);
	}

	@Override
	public Seat updateSeat(Seat seat) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			
		Seat seatUpdated= (Seat)((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).updateSeatById(seat);
		DataAccess.getTransactionManager().commit();
		
		return seatUpdated;
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

		
		

	}

	@Override
	public void deleteCustomer(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		ServiceInstance.getCustomerService().deleteById(id);
		DataAccess.getTransactionManager().commit();
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		

	}

	@Override
	public Category addCategory(Category category)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		Category categoryInserted=((Category)ServiceInstance.getCategoryService().insert(category));
		DataAccess.getTransactionManager().commit();
		return categoryInserted; 
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
	
	}

	@Override
	public void deleteCategory(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		ServiceInstance.getCategoryService().deleteById(id);
		DataAccess.getTransactionManager().commit();
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
		
	}

	@Override
	public Category updateCategory(Category category)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			
		Category categoryUpdated=(Category)((CategoryDAOMickeyImpl)ServiceInstance.getCategoryService()).updateCategoryById(category);
		DataAccess.getTransactionManager().commit();
		
		return categoryUpdated; 
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

		
		

	}

	@Override
	public Extra addExtra(Extra extra)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		Extra extraInserted=((Extra)ServiceInstance.getExtraService().insert(extra));
		DataAccess.getTransactionManager().commit();
		return extraInserted;
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		

	}

	@Override
	public void deleteExtra(long id) throws ResponseFailureException {
	// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		ServiceInstance.getExtraService().deleteById(id);
		DataAccess.getTransactionManager().commit();
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
		
		
	}

	@Override
	public Extra updateExtra(Extra extra)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
			
		Extra extraUpdated=(Extra)((ExtraDAOMickeyimpl)ServiceInstance.getExtraService()).updateExtraById(extra);
		DataAccess.getTransactionManager().commit();
		
		return extraUpdated;
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

		
		

	
	}

	@Override
	public ArrayList<Ticket> getTickets(Long msId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ((TicketDAOMickeyImpl)ServiceInstance.getTicketService()).getTicketsByMovieShowId(msId);
	}

		
	private static void deleteSeatsOnScreenUpdate(Screen screen,Long msId) throws DataAccessException,ResponseFailureException
	{
			
		Screen screenInserted=(Screen)((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).updateScreenById(screen);
		((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).deleteSeatByScreenId(screenInserted.getId());
		((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).deleteShowSeatsByMovieShowsId(msId);
		
		
		
		

		
	}

	@Override
	public ArrayList<Customer> getCustomers() throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getCustomerService().getRows(null);
	}

	@Override
	public ArrayList<ShowSeat> addShowSeat(
			ArrayList<ShowSeat> showSeats)
			throws DataAccessException,ResponseFailureException {
		// TODO Auto-generated method stub
			
		    ArrayList<ShowSeat> showSeatList=ServiceInstance.getShowSeatService().insertMultipleRows(showSeats);
		    return showSeatList;
			
		
		
		
	}
	
	
	@Override
	public void deleteShowSeat(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			DataAccess.getTransactionManager().begin();
		ServiceInstance.getShowSeatService().deleteById(id);
		DataAccess.getTransactionManager().commit();
		}
		catch(DataAccessException e)
		{
			try {
				DataAccess.getTransactionManager().rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new ResponseFailureException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
	}

	

}
