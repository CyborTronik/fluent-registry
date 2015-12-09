Feature: Show company details
  As a manager I would like to see company details based on company ID

  Background: Setup database
    Given database has no users.
    And no any company
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_COMPANIES role
    And login as stanislav@trifan.com with password 's3cr3t'
    And have no variables
    But put /companies with { name: "MegaCom", logoPath:"http://url.any", description: "Any description" }
    And persist id variable

  Scenario: Try to see an company that doesn't exist
    When get /companies/invalid_id
    Then response code is 400
    And response contains: Company not found please check the request

  Scenario: See company details
    When get /companies/${id}
    Then response code is 200
    And response contains: "name":"MegaCom","logoPath":"http://url.any","description":"Any description"