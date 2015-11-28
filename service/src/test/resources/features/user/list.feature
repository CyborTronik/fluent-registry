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
    And response list counts 1
    And response contains: "email":"stanislav@trifan.com"


  Scenario: Listing 3 users
    Given having account test1@trifan.com with password 's3cr3t'
    And having account test2@trifan.com with password 's3cr3t'
    When get /users
    Then response code is 200
    And response list counts 3
    And response contains: "email":"stanislav@trifan.com"
    And response contains: "email":"test1@trifan.com"
    And response contains: "email":"test2@trifan.com"


  Scenario: Filter by email
    Given having account test1@trifan.com with password 's3cr3t'
    And having account test2@trifan.com with password 's3cr3t'
    When get /users?email=test1
    Then response code is 200
    And response list counts 1
    And response contains: "email":"test1@trifan.com"

  Scenario: Filter by display name
    Given having account test1@trifan.com with password 's3cr3t'
    And having account test2@trifan.com with password 's3cr3t'
    When get /users?displayName=test
    Then response code is 200
    And response list counts 2
    And response contains: "email":"test1@trifan.com"
    And response contains: "email":"test2@trifan.com"


  Scenario: Order by email
    Given having account test3@trifan.com with password 's3cr3t'
    And having account test2@trifan.com with password 's3cr3t'
    When get /users?orderBy=email
    Then response code is 200
    And response list counts 3
    And response item 1 contains email stanislav@trifan.com
    And response item 2 contains email test2@trifan.com
    And response item 3 contains email test3@trifan.com

  Scenario: Order by email ascending
    Given having account test3@trifan.com with password 's3cr3t'
    And having account test2@trifan.com with password 's3cr3t'
    When get /users?orderBy=email%20asc
    Then response code is 200
    And response list counts 3
    And response item 1 contains email stanislav@trifan.com
    And response item 2 contains email test2@trifan.com
    And response item 3 contains email test3@trifan.com

  Scenario: Order by email descending
    Given having account test3@trifan.com with password 's3cr3t'
    And having account test2@trifan.com with password 's3cr3t'
    When get /users?orderBy=email%20desc
    Then response code is 200
    And response list counts 3
    And response item 1 contains email stanislav@trifan.com
    And response item 2 contains email test2@trifan.com
    And response item 3 contains email test3@trifan.com