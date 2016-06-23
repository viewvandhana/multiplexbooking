package com.adventnet.moviebooking;

/** <p> Description of the table <code>Customer</code>.
 *  Column Name and Table Name of  database table  <code>Customer</code> is mapped
 * as constants in this util.</p> 
   * 
  * Primary Key for this definition is  <br>
  <ul>
  * <li> {@link #CUSTOMER_ID}
  * </ul>
 */
 
public final class CUSTOMER
{
    private CUSTOMER()
    {
    }
   
    /** Constant denoting the Table Name of this definition.
     */
    public static final String TABLE = "Customer" ;
    /**
                            * This column is an Primary Key for this Table definition. <br>
                            * Data Type of this field is <code>BIGINT</code>. <br>
                                          * This field is nullable. <br>
                                */
    public static final String CUSTOMER_ID= "CUSTOMER_ID" ;

    /*
    * The index position of the column CUSTOMER_ID in the table.
    */
    public static final int CUSTOMER_ID_IDX = 1 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String CUSTOMER_NAME= "CUSTOMER_NAME" ;

    /*
    * The index position of the column CUSTOMER_NAME in the table.
    */
    public static final int CUSTOMER_NAME_IDX = 2 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String CUSTOMER_EMAIL= "CUSTOMER_EMAIL" ;

    /*
    * The index position of the column CUSTOMER_EMAIL in the table.
    */
    public static final int CUSTOMER_EMAIL_IDX = 3 ;

    /**
                            * Data Type of this field is <code>CHAR</code>. <br>
                     * Maximum length of this field value is <code>150</code>. <br>
                                   * This field is nullable. <br>
                                */
    public static final String CUSTOMER_PHONE= "CUSTOMER_PHONE" ;

    /*
    * The index position of the column CUSTOMER_PHONE in the table.
    */
    public static final int CUSTOMER_PHONE_IDX = 4 ;

}
