package com.github.cybortronik.registry.repository.sql2o.converters;

import org.junit.Test;
import org.sql2o.converters.ConverterException;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static org.junit.Assert.*;

/**
 * Created by stanislav on 11/20/15.
 */
public class OffsetDateTimeConverterTest {

    private OffsetDateTimeConverter converter = new OffsetDateTimeConverter();

    @Test
    public void convertFromNull() throws Exception {
        OffsetDateTime convert = converter.convert(null);
        assertNull(convert);
    }

    @Test
    public void convertFromTimeStamp() throws ConverterException {
        OffsetDateTime dateTime = OffsetDateTime.now();
        Timestamp timestamp = Timestamp.from(dateTime.toInstant());
        OffsetDateTime convert = converter.convert(timestamp);
        assertNotNull(convert);
        assertEquals(dateTime, convert);
    }

    @Test
    public void toDatabaseParamNull() {
        Object databaseParam = converter.toDatabaseParam(null);
        assertNull(databaseParam);
    }

    @Test
    public void toDatabaseTimestamp() {
        OffsetDateTime now = OffsetDateTime.now();
        Object o = converter.toDatabaseParam(now);
        assertNotNull(o);
        assertTrue(o instanceof Timestamp);
        assertEquals(now.getNano(), ((Timestamp)o).getNanos());
    }
}