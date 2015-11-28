package com.github.cybortronik.registry;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by stanislav on 11/26/15.
 */
public class UrlDecoder {
    public String decode(String query) {
        if (query == null)
            return null;
        try {
            return URLDecoder.decode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
