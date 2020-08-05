Feature: Currency Rate Api - GET historical data by date

  Scenario Outline: Get currency rates for historical dates (from 1999-01-04)
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<base>"
    When I request url "<date>"
    Then I get status "200"
    And response contains expected response body for date "<date>" and queryParams: `base` "<base>" and `symbols` "<symbol>"
    Examples:
    |date      |base|symbol         |
    |1999-01-04|    |               |
    |2010-01-12|    |               |
    |2020-01-30|    |               |
    |2020-01-01|USD |               |
    |2020-01-01|USD |GBP,PLN        |
    |2000-12-31|    |LVL,ROL,CZK,DKK|