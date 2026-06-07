package br.edu.utfpr.gabrielmoura.divisaodespesas.modelo;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromItemList(List<Item> items) {
        return items == null ? null : new Gson().toJson(items);
    }

    @TypeConverter
    public static List<Item> toItemList(String value) {
        if (value == null) return null;
        Type listType = new TypeToken<List<Item>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}

