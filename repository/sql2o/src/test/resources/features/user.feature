Feature: CRUD for users


  Background: Clean up database
    Given no users in database.

  Scenario: Create user
    When create user: Stanislav Trifan, strifan@email.com, qwertyhash
    And find user by email: strifan@email.com
    Then found user has name Stanislav Trifan
    And found user has password hash qwertyhash