package com.adventnet.moviebooking;

/** <p> Description of the table <code>Extra</code>.
 *  Column Name and Table Name of  database table  <code>Extra</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #EXTRA_ID}
  * </ul>
 */
 
public final class EXTRA
{
    private EXTRA()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Extra" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String EXTRA_ID= "EXTRA_ID" ;

    /*
    * The index position of the column EXTRA_ID in the table.
    */
    public static final int EXTRA_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String EXTRA_NAME= "EXTRA_NAME" ;

    /*
    * The index position of the column EXTRA_NAME in the table.
    */
    public static final int EXTRA_NAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>FLOAT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String COST= "COST" ;

    /*
    * The index position of the column COST in the table.
    */
    public static final int COST_IDX = 3 ;

}
