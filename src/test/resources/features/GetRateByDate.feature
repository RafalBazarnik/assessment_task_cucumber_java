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

  Scenario Outline: Historical currency rates for the same base and symbol
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<symbol>"
    When I request url "<date>"
    Then I get status "200"
    And response contains expected response body for date "<date>" and queryParams: `base` "<base>" and `symbols` "<symbol>"
    Examples:
      |date      |base|symbol|
      |2010-06-06|PLN |PLN   |


    Scenario Outline: Get currency rates for future dates (returns latest date)
      Given I set GET rates API endpoint
      And I use query param `symbols` "<symbol>"
      And I use query param `base` "<base>"
      When I request url "<date>"
      Then I get status "200"
      And response contains latest date
      Examples:
      |date      |base|symbol         |
      |3030-01-04|    |               |
      |2050-01-12|PLN |               |
      |3000-12-30|    |RON            |
      |4000-04-18|GBP |PHP            |


  Scenario Outline: Error for not supported currency symbol at date 1999-01-04
    # PHP - Philippine peso
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    When I request url "<date>"
    Then I get status "400"
    And response contains error for invalid symbol "<symbol>" for date "<date>"
    Examples:
      |date      |symbol|
      |1999-01-04|PHP   |

  Scenario Outline: Error for not supported currency base at date 1999-01-04
    # XYZ - not a currency symbol
    Given I set GET rates API endpoint
    And I use query param `base` "<symbol>"
    When I request url "<date>"
    Then I get status "400"
    And response contains error for not supported base "<symbol>"
    Examples:
      |date      |symbol|
      |1999-01-04|XYZ   |

  Scenario Outline: Error for not supported dates (older then 1999-01-04)
    Given I set GET rates API endpoint
    When I request url "<date>"
    Then I get status "400"
    And response contains error `no data for dates older then 1999-01-04`
    Examples:
      |date      |
      |1999-01-03|

  Scenario Outline: Error for dates in wrong format
    Given I set GET rates API endpoint
    When I request url "<date>"
    Then I get status "400"
    And response contains error for date of wrong format "<date>"
    Examples:
      |date     |
      |1999-01-a|

  Scenario Outline: Error for unconverted data remaining
    Given I set GET rates API endpoint
    When I request url "<date>"
    Then I get status "400"
    And response contains error for unconverted data remaining " test"
    Examples:
      |date           |
      |2005-01-07 test|

  Scenario Outline: Error for url not found
    Given I set GET rates API endpoint
    When I request url "<url>"
    Then I get status "404"
    And response contains error for url not found "<url>"
    Examples:
      |url            |
      |2002-05-13/test|
