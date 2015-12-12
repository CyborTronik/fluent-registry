Feature: CRUD for users


  Background: Clean up database
    Given no users in database.

  Scenario: Create user
    When create user: Stanislav Trifan, strifan@email.com, qwertyhash
    And find user by email: strifan@email.com
    Then found user has name Stanislav Trifan
    And found user has password hash qwertyhash
    And found user was created in less that 1 minute ago
    And found user is enabled


  Scenario: Order users by display name
    Given create user: Test1, strifan@email.com, qwertyhash
    And create user: Test3, strifan@email2.com, qwertyhash
    And create user: Test2, strifan@email3.com, qwertyhash
    When list users ordered by display name asc
    Then user number 1 has display name Test1
    Then user number 2 has display name Test2
    Then user number 3 has display name Test3

  Scenario: Order users by display name desc
    Given create user: Test1, strifan@email.com, qwertyhash
    And create user: Test3, strifan@email2.com, qwertyhash
    And create user: Test2, strifan@email3.com, qwertyhash
    When list users ordered by display name desc
    Then user number 1 has display name Test3
    Then user number 2 has display name Test2
    Then user number 3 has display name Test1


  Scenario: Check generated JSON for ordered users
    Given create user: Test1, strifan@email.com, qwertyhash
    And create user: Test3, strifan@email2.com, qwertyhash
    And create user: Test2, strifan@email3.com, qwertyhash
    When list users ordered by display name desc
    And user list is translated to JSON
    Then JSON matches (.*)Test3(.*)Test2(.*)Test1(.*)
