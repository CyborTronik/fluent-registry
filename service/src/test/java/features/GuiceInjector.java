package features;

import com.github.cybortronik.registry.RegistryModule;
import com.github.cybortronik.registry.repository.sql2o.Sql2oModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;

/**
 * Created by stanislav on 10/28/15.
 */
public class GuiceInjector implements InjectorSource {

    public static final String JDBC_URL = "jdbc:mysql://localhost:15432/registry?relaxAutoCommit=true";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "registry";

    @Override
    public Injector getInjector() {
        Sql2oModule sql2oModule = new Sql2oModule(JDBC_URL, USERNAME, PASSWORD);
        return Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, sql2oModule, new RegistryModule(), new FeaturesModule());
    }
}
