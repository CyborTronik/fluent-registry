Feature: Delete users

  Background: Setup database
    Given database has no users.
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    And login as stanislav@trifan.com with password 's3cr3t'
    And have no variables
    But persist userId variable


  Scenario: Delete non existed user
    When delete /users/no_id
    Then response code is 400

  Scenario: Delete existing user
    When delete /users/${userId}
    Then response code is 204