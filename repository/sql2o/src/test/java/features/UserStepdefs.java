package features;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.UserRepository;
import com.github.cybortronik.registry.repository.bean.FilterResult;
import com.google.gson.Gson;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by stanislav on 10/28/15.
 */
@ScenarioScoped
public class UserStepdefs {

    private UserRepository userRepository;
    private User user;
    private List<User> userList;
    private String json;

    @Inject
    public UserStepdefs(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Given("no users in database.")
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @When("create user: (.*), (.*), (.*)")
    public void createUser(String displayName, String email, String passwordHash) {
        UUID uuid = userRepository.createUser(displayName, email, passwordHash);
        assertNotNull(uuid);
    }

    @And("^find user by email: (.*)$")
    public void findUserByEmail(String email) {
        user = userRepository.findByEmail(email);
    }

    @Then("^found user has name (.*)$")
    public void found_user_has_name(String displayName) {
        assertEquals(displayName, user.getDisplayName());
    }

    @And("^found user has password hash (.*)$")
    public void found_user_has_password_hash_qwertyhash(String passwordHash) {
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @And("found user was created in less that 1 minute ago")
    public void checkCreateTime() {
        assertNotNull(user.getCreatedAt());
    }

    @And("found user is enabled")
    public void checkUserIsEnabled() {
        assertTrue(user.isEnabled());
    }


    @When("list users ordered by display name (.*)")
    public void listUsersOrderedByDisplayName(String orderDirection) {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setOrderBy("displayName " + orderDirection);
        FilterResult<User> filter = userRepository.filter(filterRequest);
        userList = filter.getEntities();
    }

    @Then("user number (.*) has display name (.*)")
    public void checkUserForIndex(int index, String displayName) {
        User user = userList.get(index - 1);
        assertEquals(displayName, user.getDisplayName());
    }

    @When("user list is translated to JSON")
    public void userListToJson() {
        json = new Gson().toJson(userList);
    }

    @Then("JSON matches (.*)")
    public void checkJsonMatch(String regExp) {
        boolean matches = json.matches(regExp);
        assertTrue("Didn't matched for " + json, matches);
    }
}
