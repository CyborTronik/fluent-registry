Feature: List companies


  Background:
    Given no any company

  Scenario: List companies
    When get /companies
    Then response code is 200
    And result list is empty


