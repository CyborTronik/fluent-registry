Feature: Update user


  Background: Setup database
    Given database has no users.
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_USERS role
    And login as stanislav@trifan.com with password 's3cr3t'
    And have no variables
    But persist userId variable

  Scenario: Try update non existed user
    When post /users/no_id with {}
    Then response code is 400
    And response contains: User not found please check the request

  Scenario: When updating without details user must remain as is.
    When post /users/${userId} with {}
    Then response code is 200
    And response contains: "displayName":"stanislav@trifan.com","email":"stanislav@trifan.com","roles":["MANAGE_USERS"]

  Scenario: Update display name
    When post /users/${userId} with { displayName: "Stanislav Trifan" }
    Then response code is 200
    And response contains: "displayName":"Stanislav Trifan"

  Scenario: Change email
    When post /users/${userId} with { email: "omg@test.in" }
    Then response code is 200
    And response contains: "email":"omg@test.in"

  Scenario: Change roles
    When post /users/${userId} with { roles: ["USER", "ADMIN"] }
    Then response code is 200
    And response contains: "roles":["ADMIN","USER"]

  Scenario: Change all user details
    When post /users/${userId} with { displayName: "Stanislav Trifan Ivan",  email: "Stanislav@Trifan.mail", roles: ["USER", "ADMIN"]  }
    Then response code is 200
    And response contains: "displayName":"Stanislav Trifan Ivan","email":"Stanislav@Trifan.mail","roles":["ADMIN","USER"]