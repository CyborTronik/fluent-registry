Feature: User must be assigned to a company

  Background:
    Given no any company

  Scenario: List companies
    When list companies
    Then response code is 200
    And result list is empty


