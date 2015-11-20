package com.github.cybortronik.registry.repository.sql2o.converters;

import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

import java.sql.Timestamp;
import java.time.*;

/**
 * Created by stanislav on 11/20/15.
 */
public class OffsetDateTimeConverter implements Converter<OffsetDateTime> {

    @Override
    public OffsetDateTime convert(Object val) throws ConverterException {
        Timestamp timestamp = (Timestamp) val;
        if (val == null)
            return null;

        Instant instant = timestamp.toInstant();
        return OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Override
    public Object toDatabaseParam(OffsetDateTime val) {
        if (val == null)
            return null;
        return Timestamp.from(val.toInstant());
    }
}
