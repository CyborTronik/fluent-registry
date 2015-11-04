Feature: Login
  As an user of current system I should be able to login and receive a JSON Web Token (JWT)

  Background: Setup database
    Given database has no users.

  Scenario: Invalid credentials
    When login as stanislav@trifan.com with password 's3cr3t'
    Then response code is 401