Feature: Currency Rate Api - GET historical data by date

  Scenario Outline: Historical Currency Rates from chosen date (from 1999-01-04)
    Given I want to get Currencies Rates from GET rates api endpoint
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

  Scenario Outline: Historical Currency Rates for the same chosen Currency Symbols and chosen Base
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<symbol>"
    When I request url "<date>"
    Then I get status "200"
    And response contains expected response body for date "<date>" and queryParams: `base` "<base>" and `symbols` "<symbol>"
    Examples:
      |date      |base|symbol|
      |2010-06-06|PLN |PLN   |


    Scenario Outline: Trying to get historical Currency Rates for future dates (returns latest date)
      Given I want to get Currencies Rates from GET rates api endpoint
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


  Scenario Outline: Trying to get historical Currency Rates for not supported Currency Symbol at chosen date
    # PHP - Philippine peso
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `symbols` "<symbol>"
    When I request url "<date>"
    Then I get status "400"
    And response contains error for invalid Currency Symbol "<symbol>" for date "<date>"
    Examples:
      |date      |symbol|
      |1999-01-04|PHP   |

  Scenario Outline: Trying to get historical Currency Rates for not supported Currency Base at chosen date
    # XYZ - not a currency symbol
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `base` "<base>"
    When I request url "<date>"
    Then I get status "400"
    And response contains error for not supported Currency Base "<base>"
    Examples:
      |date      |base|
      |1999-01-04|XYZ   |

  Scenario Outline: Trying to get historical Currency Rates for dates older then 1999-01-04
    Given I want to get Currencies Rates from GET rates api endpoint
    When I request url "<date>"
    Then I get status "400"
    And response contains error `no data for dates older then 1999-01-04`
    Examples:
      |date      |
      |1999-01-03|

  Scenario Outline: Trying to get historical Currency Rates for dates in wrong format
    Given I want to get Currencies Rates from GET rates api endpoint
    When I request url "<date>"
    Then I get status "400"
    And response contains error for date of wrong format "<date>"
    Examples:
      |date     |
      |1999-01-a|

  Scenario Outline: Trying to get historical Currency Rates from incorrect url
    Given I want to get Currencies Rates from GET rates api endpoint
    When I request url "<url>"
    Then I get status "404"
    And response contains error for url not found "<url>"
    Examples:
      |url            |
      |2002-05-13/test|
