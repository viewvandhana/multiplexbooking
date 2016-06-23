package com.adventnet.moviebooking;

/** <p> Description of the table <code>ShowDetail</code>.
 *  Column Name and Table Name of  database table  <code>ShowDetail</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #SHOW_ID}
  * </ul>
 */
 
public final class SHOWDETAIL
{
    private SHOWDETAIL()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "ShowDetail" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SHOW_ID= "SHOW_ID" ;

    /*
    * The index position of the column SHOW_ID in the table.
    */
    public static final int SHOW_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String SHOW_NAME= "SHOW_NAME" ;

    /*
    * The index position of the column SHOW_NAME in the table.
    */
    public static final int SHOW_NAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SHOW_START_TIME= "SHOW_START_TIME" ;

    /*
    * The index position of the column SHOW_START_TIME in the table.
    */
    public static final int SHOW_START_TIME_IDX = 3 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String SHOW_END_TIME= "SHOW_END_TIME" ;

    /*
    * The index position of the column SHOW_END_TIME in the table.
    */
    public static final int SHOW_END_TIME_IDX = 4 ;

}
