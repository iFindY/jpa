package de.arkadi.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {


    public static Date date(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
