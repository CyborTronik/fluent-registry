package com.github.cybortronik.registry;

import static spark.Spark.get;

/**
 * Created by stanislav on 10/26/15.
 */
public class Application {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
