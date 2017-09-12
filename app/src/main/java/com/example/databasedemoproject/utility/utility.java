package com.example.databasedemoproject.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sarabjjeet on 9/12/17.
 */

public class utility {
    public static String convertDateToString(Date date){

        String myDateFormat = "dd MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat);

        String convertedDate=sdf.format(date);

        return convertedDate;
    }

}
