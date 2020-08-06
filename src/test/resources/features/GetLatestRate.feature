Feature: Currency Rate Api - Getting latest Currency Rates

  Scenario: All latest Currency Rates with default currency Base (EUR)
    Given I want to get Currencies Rates from GET rates api endpoint
    When I request url "/latest"
    Then I get status "200"
    And response contains Base Currency "EUR"
    And response contains full list of Currency Rates without "EUR"
    And response contains latest date

  Scenario Outline: All latest Currency Rates with chosen Base
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `base` "<base>"
    When I request url "/latest"
    Then I get status "200"
    And response contains Base Currency "<base>"
    And response contains full list of Currency Rates
    And response contains latest date
    Examples:
      |base|
      |ILS |
      |ISK |
      |SEK |

  Scenario Outline: Latest Currency Rates for chosen Currency Symbols
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `symbols` "<symbol>"
    When I request url "/latest"
    Then I get status "200"
    And response contains Base Currency "EUR"
    And response contains list of Currency Rates for "<symbol>"
    And response contains latest date
    Examples:
      |symbol     |
      |USD        |
      |GBP,PLN    |
      |SEK,RON,TRY|

  Scenario Outline: Latest Currency Rates for chosen Currency Symbols with chosen Base
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<base>"
    When I request url "/latest"
    Then I get status "200"
    And response contains Base Currency "<base>"
    And response contains list of Currency Rates for "<symbol>"
    And response contains latest date
    Examples:
      |base|symbol    |
      |JPY|EUR        |
      |RUB|AUD,PLN    |
      |HUF|ZAR,RON,PHP|

  Scenario Outline: Latest Currency Rates for the same chosen Currency Symbols and chosen Base
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<symbol>"
    When I request url "/latest"
    Then I get status "200"
    And response contains Base Currency "<symbol>"
    And response contains Currency Rates for "<symbol>"
    And response Currency Rates for "<symbol>" equals "<rate>"
    And response contains latest date
    Examples:
    |symbol|rate|
    |USD   |1.0 |
    |HKD   |1.0 |
    |CZK   |1.0 |

  Scenario Outline: Trying to get latest Currency Rates for not supported Currency Symbol
    # DZD - Algerian dinar
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `symbols` "<symbol>"
    When I request url "/latest"
    Then I get status "400"
    And response contains error for invalid Currency Symbol "<symbol>" for date "<date>"
    Examples:
      |symbol|date|
      |DZD   |    |

  @FailingOnPurpose
  Scenario: Trying to get latest Currency Rates for not supported Currency Base
    # DZD - Algerian dinar
    Given I want to get Currencies Rates from GET rates api endpoint
    And I use query param `base` "DZD"
    When I request url "/latest"
#    should be: Then I get status "400"
    Then I get status "200"
    And response contains error for not supported Currency Base "DZD"

  Scenario Outline: Trying to get latest Currency Rates from incorrect url
    Given I want to get Currencies Rates from GET rates api endpoint
    When I request url "<url>"
    Then I get status "404"
    And response contains error for url not found "<url>"
    Examples:
      |url        |
      |latest/test|