package com.qs.tv.tvplayer.room;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<Integer> fromString(String value) {
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Integer> value) {
        if (value == null) {
            return "";
        } else {
            return new Gson().toJson(value);
        }
    }

}
