package features;

import com.github.cybortronik.registry.service.UserService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.runtime.java.guice.ScenarioScoped;

import javax.inject.Inject;

/**
 * Created by stanislav on 10/28/15.
 */
@ScenarioScoped
public class DatabaseStepdefs {

    private UserService userService;

    @Inject
    public DatabaseStepdefs(UserService userService) {
        this.userService = userService;
    }

    @Given("database has no users.")
    public void deleteUsers() {
        userService.deleteAll();
    }

    @Given("^having account (.*) with password '(.*)'$")
    public void createAccount(String email, String password) {
        userService.createUser(email, email, password);
    }


    @Given("(.*) has (.*) role")
    public void addUserRole(String email, String role) {
        userService.addRoleToUser(role, email);
    }
}
