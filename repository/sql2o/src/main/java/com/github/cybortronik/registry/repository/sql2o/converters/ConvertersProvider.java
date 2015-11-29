package com.github.cybortronik.registry.repository.sql2o.converters;

import com.google.gson.JsonElement;
import org.sql2o.converters.Converter;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Created by stanislav on 11/21/15.
 */
public class ConvertersProvider implements org.sql2o.converters.ConvertersProvider {
    @Override
    public void fill(Map<Class<?>, Converter<?>> mapToFill) {
        mapToFill.put(OffsetDateTime.class, new OffsetDateTimeConverter());
        mapToFill.put(JsonElement.class, new JsonElementConverter());
    }
}
