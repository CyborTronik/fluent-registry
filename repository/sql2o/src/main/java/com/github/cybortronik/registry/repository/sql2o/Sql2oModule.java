package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.repository.CompanyRepository;
import com.github.cybortronik.registry.repository.UserRepository;
import com.google.inject.AbstractModule;
import org.sql2o.Sql2o;
import org.sql2o.quirks.Quirks;
import org.sql2o.quirks.QuirksDetector;
import org.sql2o.quirks.QuirksProvider;

/**
 * Created by stanislav on 10/28/15.
 */
public class Sql2oModule extends AbstractModule {
    private String url;
    private String username;
    private String password;

    public Sql2oModule() {
        this(System.getProperty("registry.jdbc.url"), System.getProperty("registry.jdbc.username"), System.getProperty("registry.jdbc.password"));
    }

    public Sql2oModule(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void configure() {
        bind(Sql2o.class).toInstance(new Sql2o(url, username, password));
        bind(DbExecutor.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(CompanyRepository.class).to(CompanyRepositoryImpl.class);
    }
}
