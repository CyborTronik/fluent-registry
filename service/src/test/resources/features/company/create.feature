Feature: Company Management: Create companies
  As a manager I must able to create new organisation group.


  Background:
    Given no any company
    And no received JWT
    And database has no users.


  Scenario: Not allowed to create a company if user is not logged in
    When put /companies with { name: "MegaCom" }
    Then response code is 401

  Scenario: Not allowed to create a company if no rights for that
    Given having account stanislav@trifan.com with password 's3cr3t'
    And login as stanislav@trifan.com with password 's3cr3t'
    When put /companies with { name: "MegaCom" }
    Then response code is 401


  Scenario: Create a company having MANAGE_COMPANIES role
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_COMPANIES role
    And login as stanislav@trifan.com with password 's3cr3t'
    When put /companies with { name: "MegaCom" }
    Then response code is 200
    And response contains: "name":"MegaCom"

  Scenario: Create a company with description and logo path
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_COMPANIES role
    And login as stanislav@trifan.com with password 's3cr3t'
    When put /companies with { name: "MegaCom", logoPath:"http://url.any", description: "Any description" }
    Then response code is 200
    And response contains: "name":"MegaCom","logoPath":"http://url.any","description":"Any description"

  Scenario: Create a company with custom details in JSON format
    Given having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_COMPANIES role
    And login as stanislav@trifan.com with password 's3cr3t'
    When put /companies with { name: "MegaCom", details: { type: "Software" } }
    Then response code is 200
    And response contains: "details":{"type":"Software"}