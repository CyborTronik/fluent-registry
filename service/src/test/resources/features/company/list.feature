Feature: Company Management - List companies
  As a manager I must able to list all companies considering filtering, ordering and pagination.


  Background:
    Given no any company
    And database has no users.
    And having account stanislav@trifan.com with password 's3cr3t'
    And stanislav@trifan.com has MANAGE_COMPANIES role
    And login as stanislav@trifan.com with password 's3cr3t'

  Scenario: List no companies
    When get /companies
    Then response code is 200
    And result list is empty

  Scenario: List one company
    Given having company Dolce&Gabana
    When get /companies
    Then response code is 200
    And response list counts 1
    And response contains: "name":"Dolce\u0026Gabana"

  Scenario: List few companies
    Given having companies Com1, Com2, Com3
    When get /companies
    Then response code is 200
    And response list counts 3
    And response contains: "name":"Com1"
    And response contains: "name":"Com2"
    And response contains: "name":"Com3"

  Scenario: Filter companies by query
    Given having companies Com1, Com2, Com3
    When get /companies?q=2
    Then response code is 200
    And response list counts 1
    And response contains: "name":"Com2"

  Scenario: Order companies by name ascending
    Given having companies Com4, Com2, Com3
    When get /companies?orderBy=name
    Then response code is 200
    And response list counts 3
    And response item 1 contains name Com2
    And response item 2 contains name Com3
    And response item 3 contains name Com4

  Scenario: Order companies by name descending
    Given having companies Com4, Com2, Com3
    When get /companies?orderBy=name%20desc
    Then response code is 200
    And response list counts 3
    And response item 1 contains name Com4
    And response item 2 contains name Com3
    And response item 3 contains name Com2

  Scenario: Pagination feature
    Given having companies Com1, Com2, Com3, Com4, Com5, Com6
    When get /companies?limit=5
    Then response code is 200
    And response list counts 5
    And response contains: "limit":5
    And response contains: "currentPage":0
    And response contains: "totalPages":2
    When get /companies?limit=5&page=1
    Then response code is 200
    And response list counts 1
