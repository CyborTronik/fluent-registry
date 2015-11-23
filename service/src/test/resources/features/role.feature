Feature: Manage roles


  Background: Clean database
    Given no roles in database.

  Scenario: List no roles
    When get /roles
    Then response code is 200
    And result list is empty

  Scenario: List one role
    Given created ADMIN role
    When get /roles
    Then response code is 200
    And response contains: "name":"ADMIN"

  Scenario: List deleted role
    Given created ADMIN role
    And deleted ADMIN role
    When get /roles
    Then response code is 200
    And result list is empty
    And exists 1 roles in database

  Scenario: Create role
    When put /roles with { name: "ADMIN" }
    Then response code is 200
    And exists 1 roles in database


  Scenario: Delete role
    Given created ADMIN role
    When delete /roles/ADMIN
    Then response code is 204
    And exists 1 roles in database
    When get /roles
    Then response code is 200
    And result list is empty