Feature: Show user details
  As a manager I would like to see user details based on user ID

  Background: Setup database
    Given database has no users.
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    And login as stanislav@trifan.com with password 's3cr3t'
    And have no variables
    But persist userId variable

  Scenario: Try to see an user that doesn't exist
    When get /users/invalid_id
    Then response code is 400
    And response contains: User not found please check the request

  Scenario: See user details
    When get /users/${userId}
    Then response code is 200
    And response contains: "displayName":"stanislav@trifan.com","email":"stanislav@trifan.com","roles":["MANAGE_USERS"]