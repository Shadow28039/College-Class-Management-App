package com.example.finalproject2.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converter {
    @TypeConverter
//convert timestamp to date
    public static Date fromTimeStamp(Long value){ return value == null ? null: new Date(value);}
    @TypeConverter
// convert time to timestamp
    public static Long dateToTimestamp(Date date){return date == null ? null : date.getTime();}
}
