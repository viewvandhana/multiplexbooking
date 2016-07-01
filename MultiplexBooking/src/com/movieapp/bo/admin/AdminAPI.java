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
import com.movieapp.wrapperbeans.MovieShowResponseWrapper;
import com.movieapp.wrapperbeans.ScreenSeatResponseWrapper;
import com.movieapp.wrapperbeans.ScreenSeatWrapper;
import com.movieapp.wrapperbeans.SeatResponseWrapper;
public class AdminAPI implements AdminAPIInterface{

	@Override
	public Show addShow(Show show) throws ResponseFailureException {
		// TODO Auto-generated method stub
		if(((ShowDAOMickeyImpl)ServiceInstance.getShowService()).checkIfShowExist(null,show.getStartTime(),show.getEndTime())==0)
		{
		try{
		Show showInserted=(Show)ServiceInstance.getShowService().insert(show);
		return showInserted;
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());

	    }
		}
		throw new ResponseFailureException("Show already exists");
		
	
	}

	@Override
	public void deleteShow(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			if(!JoinDAO.isTicketBookedForShow(id))
			{
				ServiceInstance.getShowService().deleteById(id);
			}
			else
			{
				throw new ResponseFailureException("Ticked booked for the show.Deletion cant be performed");
			}
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
		
		if(((ShowDAOMickeyImpl)ServiceInstance.getShowService()).checkIfShowExist(show.getId(),show.getStartTime(),show.getEndTime())==0)
		{
			Show showUpdated=(Show)((ShowDAOMickeyImpl)ServiceInstance.getShowService()).updateShowById(show);
			return showUpdated;
		}
		throw new DataAccessException("Show already exists");
	
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
	public ScreenSeatResponseWrapper addScreen(ScreenSeatWrapper screenSeatWrapper) throws ResponseFailureException {
		// TODO Auto-generated method stub
		Screen screen=screenSeatWrapper.getScreenObject(); 
			
		if(((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).checkIfScreenExist(screen.getScreenName())==0)
		{	
		try{
		DataAccess.getTransactionManager().begin();
		
		Screen screenInserted=(Screen)ServiceInstance.getScreenService().insert(screen);
		
		ArrayList<Seat> seatList=screenSeatWrapper.getSeats();
		for(int i=0;i<seatList.size();i++)
		{
			Seat seat=(Seat)seatList.get(i);
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
		throw new ResponseFailureException("Screen already exists");
		
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
	public ScreenSeatResponseWrapper updateScreen(ScreenSeatWrapper screenSeatWrapper,Long screenId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		Screen screen=screenSeatWrapper.getScreenObject();
		
		try{
			DataAccess.getTransactionManager().begin();
			
		screen.setId(screenId);
		
		//update screen
		Long msId=((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).isMovieShowScheduledForScreen(screen.getId());
		if(msId!=null)
		{
		if(!JoinDAO.isTicketBookedForScreen(screen.getId()))
		{
		deleteSeatsOnScreenUpdate(screen, msId);
		ArrayList<Seat> seatList=screenSeatWrapper.getSeats();
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
			ArrayList<Seat> seatList=screenSeatWrapper.getSeats();
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
	public ScreenSeatResponseWrapper updateScreenSeats(ScreenSeatWrapper screenSeatWrapper,Long screenId)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
			
			DataAccess.getTransactionManager().begin();
			
		ArrayList<Seat> seatList=screenSeatWrapper.getSeats();
		ArrayList<Seat> seatListToBeAdded=new ArrayList<Seat>();
	
		Screen screen=screenSeatWrapper.getScreenObject();
		screen.setId(screenId);
		Long msId=((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).isMovieShowScheduledForScreen(screen.getId());
		if(msId!=null)
		{
	   		
		
		ArrayList<ShowSeat> showSeatList=new ArrayList<ShowSeat>();
			if(!((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).isTicketBookedForAnySeat(seatList))
			{
				 Screen screenUpdated=((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).updateScreenById(screen);
				 if(((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).checkIfScreenExist(screenUpdated.getScreenName())<=1)
					{	
					
					
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
				return new CommonAPI().getScreenForId(screen.getId());
				
					}
				 
					throw new DataAccessException("Screen already exists");
					
			}
			else
			{
				throw new DataAccessException("Cannot update screen seats..Ticket already booked in the selected seat(s)");
			//	throw new ResponseFailureException("Cannot update screen seats..Ticket already booked in the selected seat(s)");
				//throw message saying ticket booked.so seat cant be changed
			}
			
			
			
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
			
		Movie movieInserted=(Movie)ServiceInstance.getMovieService().insert(movie);
		return movieInserted;
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
		((MovieDAOMickeyImpl)ServiceInstance.getMovieService()).deleteById(id);
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
		Movie movieUpdated=(Movie)((MovieDAOMickeyImpl)ServiceInstance.getMovieService()).updateMovieById(movie);
		return movieUpdated; 
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

		
		


	}

	@Override
	public MovieShowResponseWrapper addMovieShow(ArrayList<MovieShow> movieShowList)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		
		ArrayList<Long> msIds=new ArrayList<Long>();
		try{
			DataAccess.getTransactionManager().begin();
		
		for(int i=0;i<movieShowList.size();i++)
		{
			MovieShow movieShow=movieShowList.get(i);
		if(((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).checkIfMovieShowExist(movieShow.getScreenID(),movieShow.getShowID(),movieShow.getMovieDate())==0)
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
		else
		{
		throw new DataAccessException("MovieShow already exists");
		}
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
		if(!((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).isTicketBookedForMovieShowId(id))
		{
			
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
		throw new ResponseFailureException("MovieShow cant be deleted as tickets are already booked for the show");
		
		
	}

	@Override
	public MovieShowResponseWrapper updateMovieShow(MovieShow movieShow)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		Long screenId=((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).getScreenIdForMovieShowId(movieShow.getId());
		
		if(!((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).isTicketBookedForMovieShowId(movieShow.getId()))
		{
			try{
				DataAccess.getTransactionManager().begin();
				
		MovieShow movieShowUpdated=(MovieShow)((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).updateMovieShowById(movieShow);
		if(((MovieShowDAOMickeyImpl)ServiceInstance.getMovieShowService()).checkIfMovieShowExist(movieShowUpdated.getScreenID(),movieShowUpdated.getShowID(),movieShowUpdated.getMovieDate())<=1)
		{  
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
		throw new DataAccessException("MovieShow already exist");
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
		    Seat seatInserted=(Seat)ServiceInstance.getSeatService().insert(seat);
		    return seatInserted;
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
			
			ArrayList<Seat> seat=new ArrayList<Seat>();
			Seat seatObj=new Seat();
			seatObj.setId(id);
			seat.add(seatObj);
			if(((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).isTicketBookedForAnySeat(seat))
			{
		     ServiceInstance.getSeatService().deleteById(id);
			}
			else
			{
				throw new ResponseFailureException("Cannot delete seat as ticket is booked");
			}
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
		
	}

	@Override
	public ArrayList<Seat> getSeats(String fields) throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getSeatService().getRowsOnSelectionCols(null, fields);

	}

	@Override
	public Seat updateSeat(Seat seat) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
		
			ArrayList<Seat> seatList=new ArrayList<Seat>();
			seatList.add(seat);
			if(((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).isTicketBookedForAnySeat(seatList))
			{
				Seat seatUpdated= (Seat)((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).updateSeatById(seat);
				return seatUpdated;
	}
			else
			{
				throw new ResponseFailureException("Cannot update seat as ticket is booked");
			}
		
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
		ServiceInstance.getCustomerService().deleteById(id);
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
		if(((CategoryDAOMickeyImpl)ServiceInstance.getCategoryService()).checkIfCategoryExist(null,category.getCategoryName())==0)
		{
		
		try{
		Category categoryInserted=((Category)ServiceInstance.getCategoryService().insert(category));
		return categoryInserted; 
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		}
		throw new ResponseFailureException("Category already exists");
		
	
	}

	@Override
	public void deleteCategory(long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
		ServiceInstance.getCategoryService().deleteById(id);
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
			if(((CategoryDAOMickeyImpl)ServiceInstance.getCategoryService()).checkIfCategoryExist(category.getId(),category.getCategoryName())==0)
			{
			
			Category categoryUpdated=(Category)((CategoryDAOMickeyImpl)ServiceInstance.getCategoryService()).updateCategoryById(category);
		    return categoryUpdated; 
		}
		throw new DataAccessException("Category already exists");
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
		if(((ExtraDAOMickeyimpl)ServiceInstance.getExtraService()).checkIfExtraExist(null,extra.getName())==0)
		{
		
		try{
		Extra extraInserted=((Extra)ServiceInstance.getExtraService().insert(extra));
		return extraInserted;
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
		}
		throw new ResponseFailureException("Extra already exists");
	

	}

	@Override
	public void deleteExtra(long id) throws ResponseFailureException {
	// TODO Auto-generated method stub
		try{
		ServiceInstance.getExtraService().deleteById(id);
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
			
			if(((ExtraDAOMickeyimpl)ServiceInstance.getExtraService()).checkIfExtraExist(extra.getId(),extra.getName())==0)
			{
			Extra extraUpdated=(Extra)((ExtraDAOMickeyimpl)ServiceInstance.getExtraService()).updateExtraById(extra);
		    return extraUpdated;
		}
		throw new DataAccessException("Extra already exists");
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
		if(((ScreenDAOMickeyImpl)ServiceInstance.getScreenService()).checkIfScreenExist(screenInserted.getScreenName())>1)
		{
		throw new DataAccessException("Screen already exists");
		}
		
		
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
		ServiceInstance.getShowSeatService().deleteById(id);
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
		
	}

	
	@Override
	public ArrayList<Customer> getCustomers( String fields)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		return ServiceInstance.getCustomerService().getRowsOnSelectionCols(null, fields);
	}
	
	@Override
	public SeatResponseWrapper getSeatByID(Long id) throws ResponseFailureException {
		// TODO Auto-generated method stub
			return ((SeatDAOMickeyImpl)ServiceInstance.getSeatService()).getSeatById(id);
		
	}
	
	
	@Override
	public ShowSeat updateShowSeat(ShowSeat showSeat)
			throws ResponseFailureException {
		// TODO Auto-generated method stub
		try{
		return ((ShowSeatDAOMickeyImpl)ServiceInstance.getShowSeatService()).updateShowSeatById(showSeat);
		}
		catch(DataAccessException e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
	}
}
