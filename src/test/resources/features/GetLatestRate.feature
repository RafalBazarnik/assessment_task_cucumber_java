Feature: Currency Rate Api - latest rates

  Scenario: All rates with default currency base
    Given I set GET rates API endpoint
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "EUR"
    And response contains full list of currency rates without "EUR"
    And response contains current date

  Scenario Outline: Rates for the same base and symbol
    Given I set GET rates API endpoint
    And I use query param `symbols` "<symbol>"
    And I use query param `base` "<symbol>"
    When I request url "/latest"
    Then I get status "200"
    And response contains base currency "<symbol>"
    And response contains rates for "<symbol>"
    And response rates for "<symbol>" equals "<rate>"
    And response contains current date
    Examples:
    |symbol|rate|
    |USD   |1.0 |
    |HKD   |1.0 |
    |CZK   |1.0 |

  Scenario: Rates for not supported currency symbol
    # DZD - Algerian dinar
    Given I set GET rates API endpoint
    And I use query param `symbols` "DZD"
    When I request url "/latest"
    Then I get status "400"
    And response contains error for invalid symbol "DZD"

  Scenario: Rates for not supported currency base
    # DZD - Algerian dinar
    Given I set GET rates API endpoint
    And I use query param `base` "DZD"
    When I request url "/latest"
    Then I get status "400"
    And response contains error for not supported base "DZD"