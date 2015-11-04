package com.github.cybortronik.registry;

import org.aeonbits.owner.Config;

/**
 * Created by stanislav on 10/28/15.
 */
@Config.Sources({"file:./registry.properties",
        "classpath:registry.properties"})
public interface RegistryProperties extends Config {
}
