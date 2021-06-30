package com.xbrain.store.util;

import java.time.LocalDate;

public class StringDateCreator {

    public static String createStringBrDate(){
        String dayString = Integer.toString(LocalDate.now().getDayOfMonth());
        String monthString = "";
        int month = LocalDate.now().getMonthValue();

        if(month < 10){
            monthString = Integer.toString(month);
            monthString = "0"+monthString;
        }

        String yearString = Integer.toString(LocalDate.now().getYear());

        return dayString+"/"+monthString+"/"+yearString;
    }
}
