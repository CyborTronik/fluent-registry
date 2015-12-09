Feature: Delete companies

  Background: Setup database
    Given database has no users.
    And no any company
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_COMPANIES role
    And login as stanislav@trifan.com with password 's3cr3t'
    And have no variables
    But put /companies with { name: "MegaCom", logoPath:"http://url.any", description: "Any description" }
    And persist id variable


  Scenario: Delete non existed company
    When delete /companies/no_id
    Then response code is 400

  Scenario: Delete existing company
    When delete /companies/${id}
    Then response code is 204