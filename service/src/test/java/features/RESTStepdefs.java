package features;

import com.jayway.restassured.response.Response;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;

import static com.jayway.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

/**
 * Created by stanislav on 10/27/15.
 */
@ScenarioScoped
public class RESTStepdefs {

    private Response response;

    @When("get (.*)")
    public void get(String url) {
        response = given().get(url);
    }

    @Then("^response contains: (.*)$")
    public void responseContains(String text) {
        String result = response.asString();
        assertTrue(result.contains(text));
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
}
