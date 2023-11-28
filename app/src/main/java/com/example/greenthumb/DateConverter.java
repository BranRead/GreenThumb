package com.example.greenthumb;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static LocalDate toDate(String dateString) {
        if(dateString == null) {
            return null;
        } else {
            return LocalDate.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDate date) {
        if(date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
