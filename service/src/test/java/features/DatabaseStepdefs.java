package features;

import com.github.cybortronik.registry.repository.CompanyRepository;
import com.github.cybortronik.registry.repository.RoleRepository;
import com.github.cybortronik.registry.service.CompanyService;
import com.github.cybortronik.registry.service.UserService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.java.guice.ScenarioScoped;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by stanislav on 10/28/15.
 */
@ScenarioScoped
public class DatabaseStepdefs {

    private UserService userService;
    private CompanyService companyService;
    private RoleRepository roleRepository;

    @Inject
    public DatabaseStepdefs(UserService userService, CompanyService companyService, RoleRepository roleRepository) {
        this.userService = userService;
        this.companyService = companyService;
        this.roleRepository = roleRepository;
    }

    @Given("database has no users.")
    public void deleteUsers() {
        userService.deleteAll();
    }

    @Given("^having account (.*) with password '(.*)'$")
    public void createAccount(String email, String password) {
        userService.createUser(email, email, password);
    }

    @Given("having company (.*)")
    public void createCompany(String companyName) {
        companyService.createCompany(companyName);
    }

    @Given("having companies (.*)")
    public void createCompanies(List<String> companyNames) {
        companyNames.forEach(companyName -> companyService.createCompany(companyName));
    }

    @Given("(.*) has (.*) role")
    public void addUserRole(String email, String role) {
        userService.addRoleToUser(role, email);
    }

    @Given("no any company")
    public void deleteAllCompanies() {
        companyService.deleteAll();
    }

    @Given("no roles in database.")
    public void deleteRoles() {
        roleRepository.deleteAll();
    }

    @Then("exists (.*) roles in database")
    public void checkRoleCount(long size) {
        assertEquals(size, roleRepository.countRoles());
    }

    @Given("created (.*) role")
    public void createRole(String role) {
        roleRepository.create(role);
    }

    @Given("deleted (.*) role")
    public void deleteRole(String role) {
        roleRepository.delete(role);
    }

}
