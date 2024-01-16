package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Util<T> {

    private static final Gson gson = new Gson();

    public static <T> String convertToJson(final T object) {
        return gson.toJson(object);
    }

    public static <T> List<T> convertJsonToList(final String listJson, final Class<T> type) {
        return gson.fromJson(listJson, TypeToken.getParameterized(List.class, type).getType());
    }

    public static <T> T convertJsonToObject(final String objectJson) {
        return gson.fromJson(objectJson, new TypeToken<T>() {
        }.getType());
    }
}
