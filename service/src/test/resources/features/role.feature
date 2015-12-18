Feature: Role Management

  Background: Clean database
    Given no roles in database.
    And database has no users.

  Scenario: Try access without role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And login as stanislav@trifan.com with password 's3cr3t'
    When get /roles
    Then response code is 401

  Scenario: List no roles
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_ROLES role
    And login as stanislav@trifan.com with password 's3cr3t'
    When get /roles
    Then response code is 200
    And result list is empty

  Scenario: List one role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_ROLES role
    And login as stanislav@trifan.com with password 's3cr3t'
    Given created ADMIN role
    When get /roles
    Then response code is 200
    And response contains: "name":"ADMIN"

  Scenario: List deleted role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_ROLES role
    And login as stanislav@trifan.com with password 's3cr3t'
    But created ADMIN role
    And deleted ADMIN role
    When get /roles
    Then response code is 200
    And result list is empty
    And exists 1 roles in database

  Scenario: Create role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_ROLES role
    And login as stanislav@trifan.com with password 's3cr3t'
    When put /roles with { name: "ADMIN" }
    Then response code is 200
    And exists 1 roles in database


  Scenario: Delete role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_ROLES role
    And login as stanislav@trifan.com with password 's3cr3t'
    But created ADMIN role
    When delete /roles/ADMIN
    Then response code is 204
    And exists 1 roles in database
    When get /roles
    Then response code is 200
    And result list is empty