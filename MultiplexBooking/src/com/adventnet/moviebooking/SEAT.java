package com.adventnet.moviebooking;

/** <p> Description of the table <code>Seat</code>.
 *  Column Name and Table Name of  database table  <code>Seat</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #SEAT_ID}
  * </ul>
 */
 
public final class SEAT
{
    private SEAT()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Seat" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SEAT_ID= "SEAT_ID" ;

    /*
    * The index position of the column SEAT_ID in the table.
    */
    public static final int SEAT_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SCREEN_ID= "SCREEN_ID" ;

    /*
    * The index position of the column SCREEN_ID in the table.
    */
    public static final int SCREEN_ID_IDX = 2 ;

    /**
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String CATEGORY_ID= "CATEGORY_ID" ;

    /*
    * The index position of the column CATEGORY_ID in the table.
    */
    public static final int CATEGORY_ID_IDX = 3 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String NAME= "NAME" ;

    /*
    * The index position of the column NAME in the table.
    */
    public static final int NAME_IDX = 4 ;

    /**
                            * Data Type of this field is <code>TINYINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String ROW_NO= "ROW_NO" ;

    /*
    * The index position of the column ROW_NO in the table.
    */
    public static final int ROW_NO_IDX = 5 ;

    /**
                            * Data Type of this field is <code>TINYINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String COL_NO= "COL_NO" ;

    /*
    * The index position of the column COL_NO in the table.
    */
    public static final int COL_NO_IDX = 6 ;

    /**
                            * Data Type of this field is <code>BOOLEAN</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String STATUS= "STATUS" ;

    /*
    * The index position of the column STATUS in the table.
    */
    public static final int STATUS_IDX = 7 ;

}
