Feature: Listing users
  As a manager I should be able to list current users with or without filtering and sorting options

  Background: Setup database
    Given database has no users.
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    And login as stanislav@trifan.com with password 's3cr3t'

  Scenario: Listing current users
    When get /users
    Then response code is 200
    And response contains: xxx