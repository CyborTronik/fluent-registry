Feature: Company Management: Update company details
  As a manager I should be able to update company details

  Background: Setup database
    Given database has no users.
    And no any company
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_COMPANIES role
    And login as stanislav@trifan.com with password 's3cr3t'
    And have no variables
    But put /companies with { name: "MegaCom", logoPath:"http://url.any", description: "Any description" }
    And persist id variable


  Scenario: Try update non existing company
    When post /companies/no_id with {}
    Then response code is 400
    And response contains: Company not found please check the request


  Scenario: When updating without details company must remain as is
    When post /companies/${id} with {}
    Then response code is 200
    And response contains: "name":"MegaCom","logoPath":"http://url.any","description":"Any description"

  Scenario: Update company name
    When post /companies/${id} with { name: "EverCom" }
    Then response code is 200
    And response contains: "name":"EverCom"

  Scenario: Update company logo
    When post /companies/${id} with { logoPath: "https://new.logo" }
    Then response code is 200
    And response contains: "logoPath":"https://new.logo"

  Scenario: Update company description
    When post /companies/${id} with { description: "New description for this company" }
    Then response code is 200
    And response contains: "description":"New description for this company"

  Scenario: Update company JSON details
    When post /companies/${id} with { details: { very: "Cool", json: "Details", description: "Super feature that allows to put any kind of data in JSON format" } }
    Then response code is 200
    And response contains: "details":{"very":"Cool","json":"Details","description":"Super feature that allows to put any kind of data in JSON format"}

  Scenario: Update all fields at once
    When post /companies/${id} with { name: "Oracle", logoPath:"http://oracle.com", description: "I think you heard about it.", details: { has_software:"yes" } }
    Then response code is 200
    And response contains: {"name":"Oracle","logoPath":"http://oracle.com","description":"I think you heard about it.","details":{"has_software":"yes"}