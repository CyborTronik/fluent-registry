package features;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.jwt.JwtClaimsAdapter;
import com.github.cybortronik.registry.jwt.JwtReader;
import com.jayway.restassured.response.Response;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;

import javax.inject.Inject;

import static com.jayway.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by stanislav on 10/27/15.
 */
@ScenarioScoped
public class RESTStepdefs {

    private Response response;
    private JwtReader jwtReader;
    private JwtClaimsAdapter jwtClaimsAdapter;
    private User user;

    @Inject
    public RESTStepdefs(JwtReader jwtReader) {
        this.jwtReader = jwtReader;
    }

    @When("get (.*)")
    public void get(String url) {
        response = given().get(url);
    }

    @Then("^response contains: (.*)$")
    public void responseContains(String text) {
        String result = response.asString();
        assertTrue("Current result doesn't contain requested text. Check please result: " + result, result.contains(text));
    }

    @When("login as (.*) with password '(.*)'")
    public void login(String email, String password) {
        String body = format("{ \"email\": \"%s\", \"password\": \"%s\"  }", email, password);
        response = given().body(body).post("/login");
    }

    @Then("^response code is (\\d+)")
    public void checkResponseCode(int responseCode) {
        response.then().statusCode(responseCode);
    }

    @When("extract JWT details")
    public void extractJwtDetails() {
        String jwt = response.jsonPath().getString("jwt");
        jwtClaimsAdapter = jwtReader.read(jwt);
        user = jwtClaimsAdapter.readUser();
    }

    @Then("JWT email is (.*)")
    public void checkJwtEmail(String email) {
        assertEquals("Email not matched, user has " + user.getEmail(), email, user.getEmail());
    }

    @Then("JWT contains role (.*)")
    public void checkJwtRole(String role) {
        assertTrue("User doesn't have the role: " + role, user.hasRole(role));
    }

    @When("request user creation for: (.*) with password \"(.*)\" as (.*)")
    public void creteUser(String email, String password, String displayName) {
        String jsonBody = format("{ \"email\": \"%s\", \"password\": \"%s\", \"confirmPassword\": \"%s\", \"displayName\":\"%s\" }", email, password, password, displayName);
        response = given().body(jsonBody).put("/users");
    }

    @When("list companies")
    public void listCompanies() {
        response = given().get("/companies");
    }

    @Then("result list is empty")
    public void checkEmptyListResponse() {
        responseContains("[]");
    }
}
