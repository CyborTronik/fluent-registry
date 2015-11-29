Feature: Create users
  As a manager I must be able to create new user

  Background: Setup database
    Given database has no users.
    And clear received JWT

  Scenario: Create user without being logged in
    When request user creation for: john@carter.com with password "an0nymus" as John Carter
    Then response code is 401

  Scenario: Create user having no rights for that
    Given having account stanislav@trifan.com with password 's3cr3t'
    And login as stanislav@trifan.com with password 's3cr3t'
    When request user creation for: john@carter.com with password "an0nymus" as John Carter
    Then response code is 401

  Scenario: Create user having rights
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    And login as stanislav@trifan.com with password 's3cr3t'
    When request user creation for: john@carter.com with password "an0nymus" as John Carter
    Then response code is 200
    And response contains: "displayName":"John Carter"

  Scenario: Create user with specific role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    And login as stanislav@trifan.com with password 's3cr3t'
    When put /users with { email:"john@carter.com", displayName:"John Carter", password: "password", passwordConfirmation: "password", roles: ["MANAGE_USERS"] }
    Then response code is 200
    And response contains: "displayName":"John Carter"
    And response contains: MANAGE_USERS

  Scenario: Create user with custom details (in JSON format)
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    And login as stanislav@trifan.com with password 's3cr3t'
    When put /users with { email:"john@carter.com", displayName:"John Carter", password: "password", passwordConfirmation: "password", details: { gender: "male"} }
    Then response code is 200
    And response contains: "displayName":"John Carter"
    And response contains: "details":{"gender":"male"}

