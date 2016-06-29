package com.movieapp.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.moviebooking.CATEGORY;
import com.adventnet.moviebooking.CUSTOMER;
import com.adventnet.moviebooking.EXTRA;
import com.adventnet.moviebooking.MOVIE;
import com.adventnet.moviebooking.MOVIESHOW;
import com.adventnet.moviebooking.SCREEN;
import com.adventnet.moviebooking.SEAT;
import com.adventnet.moviebooking.SHOWDETAIL;
import com.adventnet.moviebooking.SHOWSEAT;
import com.adventnet.moviebooking.TICKET;
import com.adventnet.moviebooking.TICKETCHARGE;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.movieapp.beans.Movie;
import com.movieapp.beans.MovieShow;
import com.movieapp.beans.Screen;
import com.movieapp.beans.Seat;
import com.movieapp.beans.Show;
import com.movieapp.beans.Ticket;
import com.movieapp.beans.TicketCharge;
import com.movieapp.daofactory.CategoryDAOFactory;
import com.movieapp.daofactory.CustomerDAOFactory;
import com.movieapp.daofactory.ExtraDAOFactory;
import com.movieapp.daofactory.MovieDAOFactoy;
import com.movieapp.daofactory.MovieShowDAOFactory;
import com.movieapp.daofactory.ScreenDAOFactory;
import com.movieapp.daofactory.SeatDAOFactory;
import com.movieapp.daofactory.ShowDAOFactory;
import com.movieapp.daofactory.ShowSeatDAOFactory;
import com.movieapp.daofactory.TicketChargeDAOFactory;
import com.movieapp.daofactory.TicketDAOFactory;
import com.movieapp.util.ObjectMapperUtil;

public class JoinDAO {

	public static String getMovieShowsOnJoinCriteria(SelectQuery moviesQuery)
			throws ResponseFailureException {

		try {

			DataObject dataObject = MovieDAOFactoy
					.getMovieDAOInstance("mickey").getDataObject(moviesQuery);

			String moviesStr = ObjectMapperUtil.getCustomMappedString(
					"movies",
					MovieDAOFactoy.getMovieDAOInstance("mickey").getBeans(
							dataObject.getRows(MOVIE.TABLE)));
			String actualMovies = moviesStr.subSequence(1,
					moviesStr.length() - 1).toString();
			ArrayList<MovieShow> movieShows = new ArrayList<MovieShow>();

			Iterator rows = dataObject.getRows(MOVIESHOW.TABLE);
			while (rows.hasNext()) {
				Row row = (Row) rows.next();
				MovieShow movieShow = (MovieShow) MovieShowDAOFactory
						.getMovieShowDAOInstance("mickey").getAdapter().asBean(row);
				movieShow
						.setAvailableSeats(((ShowSeatDAOMickeyImpl) ShowSeatDAOFactory
								.getShowSeatDAOInstance("mickey"))
								.getAvailableSeatForMovieShow(movieShow.getId()));
				movieShows.add(movieShow);

			}

			String ms = ObjectMapperUtil.getCustomMappedString("movieshows",
					movieShows);
			String actualMovieShow = ms.subSequence(1, ms.length() - 1)
					.toString();

			String screenStr = ObjectMapperUtil.getCustomMappedString(
					"screens", ScreenDAOFactory.getScreenDAOInstance("mickey")
							.getBeans(dataObject.getRows(SCREEN.TABLE)));
			String actualScreens = screenStr.subSequence(1,
					screenStr.length() - 1).toString();

			String showStr = ObjectMapperUtil.getCustomMappedString(
					"shows",
					ShowDAOFactory.getShowDAOInstance("mickey").getBeans(
							dataObject.getRows(SHOWDETAIL.TABLE)));
			String actualShows = showStr.subSequence(1, showStr.length() - 1)
					.toString();

			String finalResponse = "{" + actualMovieShow + "," + actualMovies
					+ "," + actualScreens + "," + actualShows + "}";
			return finalResponse;
		} catch (Exception e) {
			throw new ResponseFailureException(e.getMessage());
		}

	}

	

	
	
		public static SelectQuery constructMovieshowSelectQueryOnJoin(Long msId)
	{
		SelectQuery moviesQuery = new SelectQueryImpl(
				Table.getTable(MOVIESHOW.TABLE));
		Criteria criteria = new Criteria(Column.getColumn(MOVIESHOW.TABLE, MOVIESHOW.MOVIE_SHOW_ID),msId,QueryConstants.EQUAL);
		
		moviesQuery.setCriteria(criteria);
		return addMovieShowSelectColsAndJoins(moviesQuery);
	}

	public static SelectQuery addMovieShowSelectColsAndJoins(SelectQuery moviesQuery)
	{
		moviesQuery.addSelectColumn(new Column(MOVIE.TABLE, "*"));
		moviesQuery.addSelectColumn(new Column(MOVIESHOW.TABLE, "*"));
		moviesQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE, "*"));
		moviesQuery.addSelectColumn(new Column(SCREEN.TABLE, "*"));
	
		Join movieJoin = new Join(MOVIESHOW.TABLE, MOVIE.TABLE,
				new String[] { MOVIESHOW.MOVIE_ID }, new String[] { MOVIE.MOVIE_ID },
				Join.INNER_JOIN);
		Join screenJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE,
				new String[] { MOVIESHOW.SCREEN_ID }, new String[] { SCREEN.SCREEN_ID },
				Join.INNER_JOIN);
		Join showJoin = new Join(MOVIESHOW.TABLE, SHOWDETAIL.TABLE,
				new String[] { MOVIESHOW.SHOW_ID }, new String[] { SHOWDETAIL.SHOW_ID },
				Join.INNER_JOIN);

	
		moviesQuery.addJoin(movieJoin);
		moviesQuery.addJoin(screenJoin);
		moviesQuery.addJoin(showJoin);
		
		return moviesQuery;

	}
	

	public static String getBookTicketMessage(Long msId) throws ResponseFailureException {

		try{
		
		DataObject dataObject=MovieShowDAOFactory.getMovieShowDAOInstance("mickey").getDataObject(constructMovieshowSelectQueryOnJoin(msId));

	
		Screen screen=(Screen) ScreenDAOFactory.getScreenDAOInstance("mickey")
				.getBeans(dataObject.getRows(SCREEN.TABLE)).get(0);
		Movie movie=(Movie) MovieDAOFactoy.getMovieDAOInstance("mickey")
				.getBeans(dataObject.getRows(MOVIE.TABLE)).get(0);
		Show show=(Show) ShowDAOFactory.getShowDAOInstance("mickey")
				.getBeans(dataObject.getRows(SHOWDETAIL.TABLE)).get(0);
		
		String bookTicketMessage= "Movie:"+movie.getMovieName()+"\n"+"Screen:"+screen.getScreenName()+"\n"+"Show:"+show.getShowName()+" "+show.getStartTime();
		return bookTicketMessage;
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}

		
	}
	
	public static String getScreensOnJoinCriteria(Criteria criteria) throws ResponseFailureException
	{
		try{
			SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(SCREEN.TABLE)); 
		    selectQuery.addSelectColumn(new Column(CATEGORY.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SEAT.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SCREEN.TABLE,"*"));
			selectQuery.setCriteria(criteria);
			Join screenJoin = new Join(SCREEN.TABLE, SEAT.TABLE, new String[]{SCREEN.SCREEN_ID}, new String[]{SEAT.SCREEN_ID},Join.LEFT_JOIN); 
		    Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE, new String[]{SEAT.CATEGORY_ID}, new String[]{CATEGORY.CATEGORY_ID},Join.LEFT_JOIN); 
		   
		    selectQuery.addJoin(screenJoin);
		    selectQuery.addJoin(categoryJoin);
		    
		    DataObject dataObject=ScreenDAOFactory.getScreenDAOInstance("mickey").getDataObject(selectQuery); 
		   
		//    String screenStr=ObjectMapperUtil.getCustomMappedString("screens",ScreenDAOFactory.getScreenDAOInstance("mickey").getBeans(dataObject.getRows(SCREEN.TABLE)));
		  //  String actualScreens=screenStr.subSequence(1,screenStr.length()-1).toString();
		    
		    JSONArray seatIdArray=new JSONArray();
		    JSONArray screenArray=new JSONArray();
			      
		    ArrayList<Seat> seatList=SeatDAOFactory.getSeatDAOInstance("mickey").getBeans(dataObject.getRows(SEAT.TABLE));
		    ArrayList<Screen> screenList=ScreenDAOFactory.getScreenDAOInstance("mickey").getBeans(dataObject.getRows(SCREEN.TABLE));
			
		    
		    for(int i=0;i<seatList.size();i++)
		    {
		    	seatIdArray.put(seatList.get(i).getId());
		    }

		    
		    String seatStr=ObjectMapperUtil.getCustomMappedString("seats",seatList);
		    String actualSeats=seatStr.subSequence(1,seatStr.length()-1).toString();
			
		    String categoryStr=ObjectMapperUtil.getCustomMappedString("categories",CategoryDAOFactory.getCategoryDAOInstance("mickey").getBeans(dataObject.getRows(CATEGORY.TABLE)));
		    String actualCategory=categoryStr.subSequence(1,categoryStr.length()-1).toString();
		    
		    for(int i=0;i<screenList.size();i++)
		    {
		   
		    	    Screen screen=screenList.get(i);
				    JSONObject screenJson=new JSONObject();
				    screenJson.put("id",screen.getId());
				    screenJson.put("screenName",screen.getScreenName());
				    screenJson.put("screenRows",screen.getScreenRows());
				    screenJson.put("screenColumns",screen.getScreenColumns());
				    screenJson.put("seats", seatIdArray);
				    screenArray.put(screenJson);
					    
		    	
		    }
		   
		   
		    
		    String finalResponse="{\"screens\":"+screenArray+","+actualSeats+","+actualCategory+"}";
		    return finalResponse;
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
			
		

	}

	

	public static String getSeatsForScreenOnJoinCriteria(Criteria criteria) throws ResponseFailureException
	{
		try{
			SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(SEAT.TABLE)); 
		    selectQuery.addSelectColumn(new Column(CATEGORY.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SEAT.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SCREEN.TABLE,"*"));
			selectQuery.setCriteria(criteria);
			Join screenJoin = new Join(SEAT.TABLE, SCREEN.TABLE, new String[]{SEAT.SCREEN_ID}, new String[]{SCREEN.SCREEN_ID},Join.LEFT_JOIN); 
		    Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE, new String[]{SEAT.CATEGORY_ID}, new String[]{CATEGORY.CATEGORY_ID},Join.LEFT_JOIN); 
		   
		    selectQuery.addJoin(screenJoin);
		    selectQuery.addJoin(categoryJoin);
		    
		    DataObject dataObject=SeatDAOFactory.getSeatDAOInstance("mickey").getDataObject(selectQuery); 
		   
		//    String screenStr=ObjectMapperUtil.getCustomMappedString("screens",ScreenDAOFactory.getScreenDAOInstance("mickey").getBeans(dataObject.getRows(SCREEN.TABLE)));
		  //  String actualScreens=screenStr.subSequence(1,screenStr.length()-1).toString();
		    
		   
			      
		    ArrayList<Seat> seatList=SeatDAOFactory.getSeatDAOInstance("mickey").getBeans(dataObject.getRows(SEAT.TABLE));
		    Screen screen=(Screen)ScreenDAOFactory.getScreenDAOInstance("mickey").getBeans(dataObject.getRows(SCREEN.TABLE)).get(0);
			ArrayList<Screen> screenList=new ArrayList<Screen>();
			screenList.add(screen);
		   
		    
		    String seatStr=ObjectMapperUtil.getCustomMappedString("seats",seatList);
		    String actualSeats=seatStr.subSequence(1,seatStr.length()-1).toString();
			
		    String categoryStr=ObjectMapperUtil.getCustomMappedString("categories",CategoryDAOFactory.getCategoryDAOInstance("mickey").getBeans(dataObject.getRows(CATEGORY.TABLE)));
		    String actualCategory=categoryStr.subSequence(1,categoryStr.length()-1).toString();
		    
		    String screenStr=ObjectMapperUtil.getCustomMappedString("screens",screenList);
		    String actualScreen=screenStr.subSequence(1,screenStr.length()-1).toString();
		   
		    
		    String finalResponse="{"+actualSeats+","+actualCategory+","+actualScreen+"}";
		    return finalResponse;
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
			
		

	}

	


	
	
	public static String getShowSeatsOnJoinCriteria(Criteria criteria) throws ResponseFailureException
	{
		try{
			SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(SHOWSEAT.TABLE)); 
		    selectQuery.addSelectColumn(new Column(CATEGORY.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SEAT.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SCREEN.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE,"*"));
		    selectQuery.addSelectColumn(new Column(MOVIESHOW.TABLE,"*"));
				
		    selectQuery.setCriteria(criteria);
			Join showSeatJoin = new Join(SHOWSEAT.TABLE, SEAT.TABLE, new String[]{SHOWSEAT.SEAT_ID}, new String[]{SEAT.SEAT_ID},Join.INNER_JOIN); 
		    Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE, new String[]{SEAT.CATEGORY_ID}, new String[]{CATEGORY.CATEGORY_ID},Join.INNER_JOIN); 
		    Join movieShowJoin = new Join(SHOWSEAT.TABLE, MOVIESHOW.TABLE, new String[]{SHOWSEAT.MOVIE_SHOW_ID}, new String[]{MOVIESHOW.MOVIE_SHOW_ID},Join.INNER_JOIN); 
		    Join movieJoin = new Join(MOVIESHOW.TABLE, MOVIE.TABLE, new String[]{MOVIESHOW.MOVIE_ID}, new String[]{MOVIE.MOVIE_ID},Join.INNER_JOIN); 
		    Join showJoin = new Join(MOVIESHOW.TABLE, SHOWDETAIL.TABLE, new String[]{MOVIESHOW.SHOW_ID}, new String[]{SHOWDETAIL.SHOW_ID},Join.INNER_JOIN); 
		    Join screenJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE, new String[]{MOVIESHOW.SCREEN_ID}, new String[]{SCREEN.SCREEN_ID},Join.INNER_JOIN); 
			
			   
		    selectQuery.addJoin(movieShowJoin);
			selectQuery.addJoin(showSeatJoin);
		    selectQuery.addJoin(categoryJoin);
		    selectQuery.addJoin(screenJoin);
		    selectQuery.addJoin(showJoin);
		    selectQuery.addJoin(movieJoin);
			
		    DataObject dataObject=ShowSeatDAOFactory.getShowSeatDAOInstance("mickey").getDataObject(selectQuery); 
		   
		    String showSeatStr=ObjectMapperUtil.getCustomMappedString("showseats",ShowSeatDAOFactory.getShowSeatDAOInstance("mickey").getBeans(dataObject.getRows(SHOWSEAT.TABLE)));
		    String actualShowSeats=showSeatStr.subSequence(1,showSeatStr.length()-1).toString();
		    
		    String seatStr=ObjectMapperUtil.getCustomMappedString("seats",SeatDAOFactory.getSeatDAOInstance("mickey").getBeans(dataObject.getRows(SEAT.TABLE)));
		    String actualSeats=seatStr.subSequence(1,seatStr.length()-1).toString();
			
		    String categoryStr=ObjectMapperUtil.getCustomMappedString("categories",CategoryDAOFactory.getCategoryDAOInstance("mickey").getBeans(dataObject.getRows(CATEGORY.TABLE)));
		    String actualCategory=categoryStr.subSequence(1,categoryStr.length()-1).toString();
		    
		    String movieShowStr=ObjectMapperUtil.getCustomMappedString("movieshows",MovieShowDAOFactory.getMovieShowDAOInstance("mickey").getBeans(dataObject.getRows(MOVIESHOW.TABLE)));
		    String actualMovieShow=movieShowStr.subSequence(1,movieShowStr.length()-1).toString();
			
		    String screenStr=ObjectMapperUtil.getCustomMappedString("screens",ScreenDAOFactory.getScreenDAOInstance("mickey").getBeans(dataObject.getRows(SCREEN.TABLE)));
		    String actualScreens=screenStr.subSequence(1,screenStr.length()-1).toString();
			
		    String showStr=ObjectMapperUtil.getCustomMappedString("shows",ShowDAOFactory.getShowDAOInstance("mickey").getBeans(dataObject.getRows(SHOWDETAIL.TABLE)));
		    String actualShows=showStr.subSequence(1,showStr.length()-1).toString();
			
		    String movieStr=ObjectMapperUtil.getCustomMappedString("movies",MovieDAOFactoy.getMovieDAOInstance("mickey").getBeans(dataObject.getRows(MOVIE.TABLE)));
		    String actualMovies=movieStr.subSequence(1,movieStr.length()-1).toString();
			
		    
		    String finalResponse="{"+actualShowSeats+","+actualSeats+","+actualMovieShow+","+actualScreens+","+actualShows+","+actualMovies+","+actualCategory+"}";
		    return finalResponse;
			
			}
			catch(Exception e)
			{
				throw new ResponseFailureException(e.getMessage());
			}

	}

	

	
	
	public static HashMap<String,String> getTicketDetail(Long ticketId,boolean isExtraAvailable) throws ResponseFailureException
	{
	
		try{
			
			HashMap<String, String> responseValues=new HashMap<String, String>();
			
			
			SelectQuery joinQuery = new SelectQueryImpl(Table.getTable( TICKET.TABLE)); 
			Criteria criteria=new Criteria(new Column(TICKET.TABLE,TICKET.TICKET_ID),ticketId,QueryConstants.EQUAL);
			
			joinQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE,"*"));
		    joinQuery.addSelectColumn(new Column(SEAT.TABLE,"*"));
		    joinQuery.addSelectColumn(new Column(CATEGORY.TABLE,"*"));
		    joinQuery.addSelectColumn(new Column(TICKET.TABLE,"*"));
		    joinQuery.addSelectColumn(new Column(CUSTOMER.TABLE,"*"));
		    joinQuery.addSelectColumn(new Column(SCREEN.TABLE,"*"));
			joinQuery.addSelectColumn(new Column(MOVIE.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(MOVIESHOW.TABLE, "*"));
			joinQuery.addSelectColumn(new Column(SHOWDETAIL.TABLE, "*"));
			
			    
		    joinQuery.setCriteria(criteria);
			    
		    
		    Join seatJoin = new Join(SHOWSEAT.TABLE, SEAT.TABLE, new String[]{SHOWSEAT.SEAT_ID}, new String[]{SEAT.SEAT_ID},Join.INNER_JOIN); 
		    Join categoryJoin = new Join(SEAT.TABLE, CATEGORY.TABLE, new String[]{SEAT.CATEGORY_ID}, new String[]{CATEGORY.CATEGORY_ID},Join.INNER_JOIN); 
		    Join showSeatJoin = new Join(TICKET.TABLE, SHOWSEAT.TABLE, new String[]{TICKET.TICKET_ID}, new String[]{SHOWSEAT.TICKET_ID},Join.INNER_JOIN); 
		    Join customerJoin = new Join(TICKET.TABLE, CUSTOMER.TABLE, new String[]{TICKET.CUSTOMER_ID}, new String[]{CUSTOMER.CUSTOMER_ID},Join.INNER_JOIN); 
		    Join movieShowJoin = new Join(TICKET.TABLE, MOVIESHOW.TABLE, new String[]{TICKET.MOVIE_SHOW_ID}, new String[]{MOVIESHOW.MOVIE_SHOW_ID},Join.INNER_JOIN); 
		    Join movieJoin = new Join(MOVIESHOW.TABLE, MOVIE.TABLE, new String[]{MOVIESHOW.MOVIE_ID}, new String[]{MOVIE.MOVIE_ID},Join.INNER_JOIN); 
		    Join showJoin = new Join(MOVIESHOW.TABLE, SHOWDETAIL.TABLE, new String[]{MOVIESHOW.SHOW_ID}, new String[]{SHOWDETAIL.SHOW_ID},Join.INNER_JOIN); 
		    Join screenJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE, new String[]{MOVIESHOW.SCREEN_ID}, new String[]{SCREEN.SCREEN_ID},Join.INNER_JOIN); 
		 	      
		    
		    joinQuery.addJoin(showSeatJoin);
			joinQuery.addJoin(seatJoin);
		    joinQuery.addJoin(categoryJoin);
		    
		    joinQuery.addJoin(customerJoin);
		    
		    joinQuery.addJoin(movieShowJoin);
		    joinQuery.addJoin(screenJoin);
		    joinQuery.addJoin(showJoin);
		    joinQuery.addJoin(movieJoin);
			          
			DataObject dataObject = TicketDAOFactory.getTicketDAOInstance("mickey").getDataObject(joinQuery);
			
			SelectQuery chargesJoinQuery = new SelectQueryImpl(Table.getTable( TICKET.TABLE)); 
			chargesJoinQuery.setCriteria(criteria);
		    chargesJoinQuery.addSelectColumn(new Column(TICKET.TABLE,"*"));
			chargesJoinQuery.addSelectColumn(new Column(EXTRA.TABLE, "*"));
	    	chargesJoinQuery.addSelectColumn(new Column(TICKETCHARGE.TABLE,"*"));
	    	Join chargeJoin = new Join(TICKET.TABLE, TICKETCHARGE.TABLE, new String[]{TICKET.TICKET_ID}, new String[]{TICKETCHARGE.TICKET_ID},Join.LEFT_JOIN); 
	    	Join extraJoin = new Join(TICKETCHARGE.TABLE, EXTRA.TABLE, new String[]{TICKETCHARGE.EXTRA_ID}, new String[]{EXTRA.EXTRA_ID},Join.LEFT_JOIN); 
	   	    chargesJoinQuery.addJoin(chargeJoin);
	  		chargesJoinQuery.addJoin(extraJoin);
	  		DataObject chargesDataObject = TicketDAOFactory.getTicketDAOInstance("mickey").getDataObject(chargesJoinQuery);
			
			
	
		    String categoryStr=ObjectMapperUtil.getCustomMappedString("categories",CategoryDAOFactory.getCategoryDAOInstance("mickey").getBeans(dataObject.getRows(CATEGORY.TABLE)));
		    String actualCategories=categoryStr.subSequence(1,categoryStr.length()-1).toString();
		
		    ArrayList<Seat> seatList=SeatDAOFactory.getSeatDAOInstance("mickey").getBeans(dataObject.getRows(SEAT.TABLE));
		   
		    ArrayList<TicketCharge> ticketChargeList=TicketChargeDAOFactory.getTicketChargeDAOInstance("mickey").getBeans(chargesDataObject.getRows(TICKETCHARGE.TABLE));
		    
		    String seatStr=ObjectMapperUtil.getCustomMappedString("seats",seatList);
		    String actualSeats=seatStr.subSequence(1,seatStr.length()-1).toString();
		    String seatNameStr="Seats:";
		    JSONArray seatIdArray=new JSONArray();
		    JSONArray ticketChargeIdArray=new JSONArray();

		    
		    for(int i=0;i<seatList.size();i++)
		    {
		    	seatIdArray.put(seatList.get(i).getId());
		    	seatNameStr=seatNameStr+seatList.get(i).getName();
		    }

		   	
			 
		   
		   	for(int i=0;i<ticketChargeList.size();i++)
		    {
		   		ticketChargeIdArray.put(ticketChargeList.get(i).getId());
			    
		    }
		 
		  	
		   	
		    Ticket ticket=(Ticket)TicketDAOFactory.getTicketDAOInstance("mickey").getBeans(dataObject.getRows(TICKET.TABLE)).get(0);
		    JSONObject ticketJson=new JSONObject();
		    ticketJson.put("id",ticket.getId());
		    ticketJson.put("movieShowID",ticket.getMovieShowID());
		    ticketJson.put("customerID",ticket.getCustomerID());
		    ticketJson.put("totalCost",ticket.getTotalCost());
		    ticketJson.put("seats", seatIdArray);
		    if(ticketChargeIdArray.length()>0)
		    {
		    ticketJson.put("ticketcharges", ticketChargeIdArray);
		    }
			    
		   
		    //String ticketStr=ObjectMapperUtil.getCustomMappedString("tickets",new JSONObject(response));
		    //String actualTickets=ticketStr.subSequence(1,ticketStr.length()-1).toString();
			
		    String chargeStr=ObjectMapperUtil.getCustomMappedString("ticketcharges",ticketChargeList);
		    String actualCharges=chargeStr.subSequence(1,chargeStr.length()-1).toString();
			
		    String movieShowStr=ObjectMapperUtil.getCustomMappedString("movieshows",MovieShowDAOFactory.getMovieShowDAOInstance("mickey").getBeans(dataObject.getRows(MOVIESHOW.TABLE)));
		    String actualMovieShow=movieShowStr.subSequence(1,movieShowStr.length()-1).toString();
			
		    String screenStr=ObjectMapperUtil.getCustomMappedString("screens",ScreenDAOFactory.getScreenDAOInstance("mickey").getBeans(dataObject.getRows(SCREEN.TABLE)));
		    String actualScreens=screenStr.subSequence(1,screenStr.length()-1).toString();
			
		    String showStr=ObjectMapperUtil.getCustomMappedString("shows",ShowDAOFactory.getShowDAOInstance("mickey").getBeans(dataObject.getRows(SHOWDETAIL.TABLE)));
		    String actualShows=showStr.subSequence(1,showStr.length()-1).toString();
			
		    String movieStr=ObjectMapperUtil.getCustomMappedString("movies",MovieDAOFactoy.getMovieDAOInstance("mickey").getBeans(dataObject.getRows(MOVIE.TABLE)));
		    String actualMovies=movieStr.subSequence(1,movieStr.length()-1).toString();
			
		    String extraStr=ObjectMapperUtil.getCustomMappedString("extras",ExtraDAOFactory.getExtraDAOInstance("mickey").getBeans(chargesDataObject.getRows(EXTRA.TABLE)));
		    String actualExtras=extraStr.subSequence(1,extraStr.length()-1).toString();
			
		    String customerStr=ObjectMapperUtil.getCustomMappedString("customers",CustomerDAOFactory.getCustomerDAOInstance("mickey").getBeans(dataObject.getRows(CUSTOMER.TABLE)));
		    String actualCustomer=customerStr.subSequence(1,customerStr.length()-1).toString();
			
		    String finalResponse="{\"ticket\":["+ticketJson.toString()+"]"+","+actualSeats+","+actualCategories+","+actualCharges+","+actualExtras+","+actualCustomer+","+actualMovieShow+","+actualScreens+","+actualShows+","+actualMovies+"}";
		    responseValues.put("finalResponse", finalResponse);
		    responseValues.put("seatNames", seatNameStr);
			  
		    return responseValues;
			}
			catch(Exception e)
			{
				throw new ResponseFailureException(e.getMessage());
			}
	
		
		
	}
	
	public static boolean isTicketBookedForScreen(Long screenId) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(MOVIESHOW.TABLE)); 
	    Criteria criteria=new Criteria(new Column(MOVIESHOW.TABLE,MOVIESHOW.DATE),new Date(),QueryConstants.GREATER_EQUAL);
	    criteria=criteria.and(new Criteria(new Column(SEAT.TABLE,SEAT.SCREEN_ID),screenId,QueryConstants.EQUAL));
	    criteria=criteria.and(new Criteria(new Column(MOVIESHOW.TABLE,MOVIESHOW.SCREEN_ID),screenId,QueryConstants.EQUAL));
	    criteria=criteria.and(new Column(SHOWSEAT.TABLE,SHOWSEAT.TICKET_ID),null,QueryConstants.NOT_EQUAL);
	    selectQuery.addSelectColumn(new Column(SCREEN.TABLE,"*"));
	    selectQuery.addSelectColumn(new Column(MOVIESHOW.TABLE,"*"));
		selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE,"*"));
	    selectQuery.addSelectColumn(new Column(SEAT.TABLE,"*"));
	    selectQuery.setCriteria(criteria);
	    Join seatJoin = new Join(SEAT.TABLE, SHOWSEAT.TABLE, new String[]{SEAT.SEAT_ID}, new String[]{SHOWSEAT.SEAT_ID},Join.INNER_JOIN); 
	    Join movieShowJoin = new Join(MOVIESHOW.TABLE, SCREEN.TABLE, new String[]{MOVIESHOW.SCREEN_ID}, new String[]{SCREEN.SCREEN_ID},Join.INNER_JOIN); 
		Join screenJoin = new Join(SCREEN.TABLE, SEAT.TABLE, new String[]{SCREEN.SCREEN_ID}, new String[]{SEAT.SCREEN_ID},Join.INNER_JOIN); 
	    selectQuery.addJoin(movieShowJoin);
	    selectQuery.addJoin(screenJoin);
	    selectQuery.addJoin(seatJoin);
	    DataObject dataObject = TicketDAOFactory.getTicketDAOInstance("mickey").getDataObject(selectQuery);
		try{
		if(((Iterator)dataObject.getRows(SHOWSEAT.TABLE)).hasNext())
		{
			return true;
		}
		return false;
		}
		catch(Exception e)
		{
			throw new ResponseFailureException(e.getMessage());
		}
	   
		
	}
	public static boolean isTicketBookedForShow(Long showId) throws ResponseFailureException
	{
		SelectQuery selectQuery = new SelectQueryImpl(Table.getTable(SHOWDETAIL.TABLE)); 
	    Criteria criteria=new Criteria(new Column(SHOWSEAT.TABLE,SHOWSEAT.TICKET_ID),null,QueryConstants.NOT_EQUAL);
	    criteria=criteria.and(new Criteria(new Column(SHOWDETAIL.TABLE,SHOWDETAIL.SHOW_ID),showId,QueryConstants.EQUAL));
	    criteria=criteria.and(new Criteria(new Column(MOVIESHOW.TABLE,MOVIESHOW.DATE),new Date(),QueryConstants.GREATER_EQUAL));
		selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE,"*"));
	    selectQuery.addSelectColumn(new Column(MOVIESHOW.TABLE,"*"));
	    selectQuery.addSelectColumn(new Column(SHOWSEAT.TABLE,"*"));
	    selectQuery.setCriteria(criteria);
	    Join movieShowJoin = new Join(MOVIESHOW.TABLE, SHOWSEAT.TABLE, new String[]{MOVIESHOW.MOVIE_SHOW_ID}, new String[]{SHOWSEAT.MOVIE_SHOW_ID},Join.INNER_JOIN); 
	    Join showJoin = new Join(SHOWDETAIL.TABLE, MOVIESHOW.TABLE, new String[]{SHOWDETAIL.SHOW_ID}, new String[]{MOVIESHOW.SHOW_ID},Join.INNER_JOIN); 
	    selectQuery.addJoin(showJoin);
	    selectQuery.addJoin(movieShowJoin);
	    DataObject dataObject = TicketDAOFactory.getTicketDAOInstance("mickey").getDataObject(selectQuery);
		
	    try{
			if(((Iterator)dataObject.getRows(SHOWSEAT.TABLE)).hasNext())
			{
				return true;
			}
			return false;
			}
			catch(Exception e)
			{
				throw new ResponseFailureException(e.getMessage());
			}
		
	}
}

