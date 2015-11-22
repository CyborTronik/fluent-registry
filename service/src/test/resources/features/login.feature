Feature: Login
  As an user of current system I should be able to login and receive a JSON Web Token (JWT)

  Background: Setup database
    Given database has no users.

  Scenario: Invalid credentials
    When login as stanislav@trifan.com with password 's3cr3t'
    Then response code is 401

  Scenario: Existing account
    Given having account stanislav@trifan.com with password 's3cr3t'
    When login as stanislav@trifan.com with password 's3cr3t'
    Then response code is 200
    And response contains: jwt


  Scenario: JWT content
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    When login as stanislav@trifan.com with password 's3cr3t'
    Then response code is 200
    And response contains: jwt
    When extract JWT details
    Then JWT email is stanislav@trifan.com
    And JWT contains role MANAGE_USERS