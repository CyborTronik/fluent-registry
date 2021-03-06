package features;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.jwt.JwtClaimsAdapter;
import com.github.cybortronik.registry.jwt.JwtReader;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by stanislav on 10/27/15.
 */
@ScenarioScoped
public class RestStepdefs {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestStepdefs.class);

    private Response response;
    private JwtReader jwtReader;
    private JwtClaimsAdapter jwtClaimsAdapter;
    private User user;
    private String jwt;
    private Map<String, Object> variables = new HashMap<>();

    @Inject
    public RestStepdefs(JwtReader jwtReader) {
        this.jwtReader = jwtReader;
    }

    @When("have no variables")
    public void clearVariables() {
        variables.clear();
    }

    @But("persist userId variable")
    public void persistUserId() {
        variables.put("userId", user.getId());
    }

    @But("persist id variable")
    public void persistId() {
        LOGGER.info("Try find id from " + response.asString());
        String id = response.jsonPath().getString("id");
        variables.put("id", id);
    }

    @When("post (.*) with (.*)")
    public void post(String url, String body) {
        String preparedUrl = processVariables(url);
        response = given().body(processVariables(body)).post(preparedUrl);
    }

    private String processVariables(String url) {
        return new StrSubstitutor(variables).replace(url);
    }

    @When("no received JWT")
    public void clearJwt() {
        jwt = null;
    }

    @When("get (.*)")
    public void get(String url) {
        response = call().get(processVariables(url));
        LOGGER.info(response.asString());
    }

    private RequestSpecification call() {
        if (isNotBlank(jwt)) {
            LOGGER.trace("Current JWT is " + jwt);
            return given().header(new Header("JWT", jwt));
        }
        return given();
    }

    @Then("^response contains: (.*)$")
    public void responseContains(String text) {
        text = processVariables(text);
        String result = response.asString();
        assertTrue("Current result doesn't contain requested text (" + text + "). Check please the result: " + result, result.contains(text));
    }

    @When("login as (.*) with password '(.*)'")
    public void login(String email, String password) {
        String body = format("{ \"email\": \"%s\", \"password\": \"%s\"  }", email, password);
        response = call().body(body).post("/login");
        extractJwtDetails();
    }

    @Then("^response code is (\\d+)")
    public void checkResponseCode(int responseCode) {
        LOGGER.info("Check status code having result:  " + response.asString());
        response.then().statusCode(responseCode);
    }

    @When("extract JWT details")
    public void extractJwtDetails() {
        if (!response.asString().contains("jwt"))
            return;
        jwt = response.jsonPath().getString("jwt");
        LOGGER.trace("Extracted JWT: " + jwt);
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
        String jsonBody = format("{ \"email\": \"%s\", \"password\": \"%s\", \"passwordConfirmation\": \"%s\", \"displayName\":\"%s\" }", email, password, password, displayName);
        response = call().body(jsonBody).put("/users");
    }

    @When("list companies")
    public void listCompanies() {
        response = call().get("/companies");
    }

    @Then("result list is empty")
    public void checkEmptyListResponse() {
        responseContains("[]");
    }

    @When("put (.*) with (.*)")
    public void put(String url, String body) {
        response = call().body(processVariables(body)).put(processVariables(url));
    }

    @When("delete (.*)")
    public void delete(String url) {
        response = call().delete(processVariables(url));
    }

    @Then("response list counts (.*)")
    public void checkListSize(int count) {
        List<String> emails = response.jsonPath().getList("entities.id");
        assertEquals(count, emails.size());
    }

    @Then("response item (.*) contains (.*) (.*)")
    public void checkResponseItem(int position, String field, String value) {
        String currentValue = response.jsonPath().getString("entities." + field + "[" + (position-1) + "]");
        assertTrue("Current value is " + currentValue, currentValue.contains(value));
    }
}
