Feature: Currency Rate Api - latest rates

  Scenario: All rates with default currency base "EUR"
    Given I set GET rates API endpoint
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "EUR"
    And response contains full list of currency rates without "EUR"
    And response contains latest date

  Scenario Outline: Rates with chosen base
    Given I set GET rates API endpoint
    And I use query param `base` "<base>"
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "<base>"
    And response contains full list of currency rates
    And response contains latest date
    Examples:
      |base|
      |ILS |
      |ISK |
      |SEK |

  Scenario Outline: Rates with chosen symbols
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "EUR"
    And response contains list of currency rates for "<symbol>"
    And response contains latest date
    Examples:
      |symbol     |
      |USD        |
      |GBP,PLN    |
      |SEK,RON,TRY|

  Scenario Outline: Rates with chosen base and symbols
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<base>"
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "<base>"
    And response contains list of currency rates for "<symbol>"
    And response contains latest date
    Examples:
      |base|symbol    |
      |JPY|EUR        |
      |RUB|AUD,PLN    |
      |HUF|ZAR,RON,PHP|

  Scenario Outline: Rates for the same base and symbol
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<symbol>"
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "<symbol>"
    And response contains rates for "<symbol>"
    And response rates for "<symbol>" equals "<rate>"
    And response contains latest date
    Examples:
    |symbol|rate|
    |USD   |1.0 |
    |HKD   |1.0 |
    |CZK   |1.0 |

  Scenario: All rates using not existing param
    Given I set GET rates API endpoint
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "EUR"
    And response contains full list of currency rates without "EUR"
    And response contains latest date

  Scenario Outline: Error for not supported currency symbol
    # DZD - Algerian dinar
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    When I request url "/latest"
    Then I get status "400"
    And response contains error for invalid symbol "<symbol>" for date "<date>"
    Examples:
      |symbol|date|
      |DZD   |    |

Scenario: Error for not supported currency base
    # DZD - Algerian dinar
    Given I set GET rates API endpoint
    And I use query param `base` "DZD"
    When I request url "/latest"
    Then I get status "400"
    And response contains error for not supported base "DZD"

  Scenario: Error for not supported currency base
    # DZD - Algerian dinar
    Given I set GET rates API endpoint
    And I use query param `base` "DZD"
    When I request url "/latest"
    Then I get status "400"
    And response contains error for not supported base "DZD"

  Scenario Outline: Error for /latest url not found
    Given I set GET rates API endpoint
    When I request url "<url>"
    Then I get status "404"
    And response contains error for url not found "<url>"
    Examples:
      |url        |
      |latest/test|