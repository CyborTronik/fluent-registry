Feature: Create user
  As a manager I must be able to create new user

  Background: Setup database
    Given database has no users.
    And clear received JWT

  Scenario: Create user without being logged in
    When request user creation for: john@carter.com with password "an0nymus" as John Carter
    Then response code is 401

  Scenario: Create user without manager role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And login as stanislav@trifan.com with password 's3cr3t'
    When request user creation for: john@carter.com with password "an0nymus" as John Carter
    Then response code is 401

  Scenario: Create user with manager role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGER role
    And login as stanislav@trifan.com with password 's3cr3t'
    When request user creation for: john@carter.com with password "an0nymus" as John Carter
    Then response code is 200
    And response contains: "displayName":"John Carter"
