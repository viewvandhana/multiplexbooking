package com.adventnet.moviebooking;

/** <p> Description of the table <code>TicketCharge</code>.
 *  Column Name and Table Name of  database table  <code>TicketCharge</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #TICKET_CHARGE_ID}
  * </ul>
 */
 
public final class TICKETCHARGE
{
    private TICKETCHARGE()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "TicketCharge" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String TICKET_CHARGE_ID= "TICKET_CHARGE_ID" ;

    /*
    * The index position of the column TICKET_CHARGE_ID in the table.
    */
    public static final int TICKET_CHARGE_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String TICKET_ID= "TICKET_ID" ;

    /*
    * The index position of the column TICKET_ID in the table.
    */
    public static final int TICKET_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String EXTRA_ID= "EXTRA_ID" ;

    /*
    * The index position of the column EXTRA_ID in the table.
    */
    public static final int EXTRA_ID_IDX = 3 ;

    /**
                            * Data Type of this field is <code>INTEGER</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String QUANTITY= "QUANTITY" ;

    /*
    * The index position of the column QUANTITY in the table.
    */
    public static final int QUANTITY_IDX = 4 ;

}
