package com.github.cybortronik.registry.repository.sql2o.converters;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

/**
 * Created by stanislav on 11/29/15.
 */
public class JsonElementConverter implements Converter<JsonElement> {

    @Override
    public JsonElement convert(Object val) throws ConverterException {
        if (val == null)
            return null;
        return new JsonParser().parse(val.toString());
    }

    @Override
    public Object toDatabaseParam(JsonElement val) {
        if (val == null)
            return null;
        return val.toString();
    }
}
