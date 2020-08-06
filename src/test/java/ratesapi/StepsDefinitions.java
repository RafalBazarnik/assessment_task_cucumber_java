package ratesapi;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

@Log4j2
public class StepsDefinitions {
    private String ENDPOINT = PropertiesLoader.get("basic_api_url");
    private Response response;
    private RequestSpecification request;
    private ResponsePOJO responsePOJO;
    private ErrorResponsePOJO errorResponsePOJO;
    private String errorResponse;

    //region Given steps implementation:

    @Given("I want to get Currencies Rates from GET rates api endpoint")
    public void setEndpoint() {
        RestAssured.baseURI = ENDPOINT;
        request = given();
    }

    @Given("^I use query param `symbols` \"([^\"]*)\"$")
    public void addSymbolsParam(String symbol) {
        log.debug("Add query param `symbols` with: " + symbol);
        request.given().queryParam("symbols", symbol);
    }

    @Given("^I use query param `base` \"([^\"]*)\"$")
    public void addBaseParam(String base) {
        log.debug("Add query param `base` with: " + base);
        request.given().queryParam("base", base);
    }

    //endregion

    //region When steps implementation:

    @When("^I request url \"([^\"]*)\"$")
    public void requestUrl(String urlPart) {
        response = request.when().get(urlPart);
        String responseString = response.body().asString();
        if (response.getStatusCode() == 200) {
            responsePOJO = MapJsonToPOJO.map(responseString, ResponsePOJO.class);
            log.debug("responsePOJO: " + responsePOJO);
        } else if (response.getStatusCode() == 404) {
            errorResponse = responseString;
            log.debug("errorResponse: " + errorResponse);
        } else {
            errorResponsePOJO = MapJsonToPOJO.map(responseString, ErrorResponsePOJO.class);
            log.debug("errorResponsePOJO: " + errorResponsePOJO);
        }
    }

    //endregion

    //region Then steps implementation:

    @Then("^I get status \"([^\"]*)\"$")
    public void assertStatusCode(String expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(Integer.parseInt(expectedStatusCode), actualStatusCode);
    }

    @Then("^response contains Base Currency \"([^\"]*)\"$")
    public void assertBaseCurrency(String expectedBaseCurrency) {
        assertEquals(expectedBaseCurrency, responsePOJO.getBase());
    }

    @Then("response contains full list of Currency Rates")
    public void assertRatesFullList() {
        assertTrue(Arrays.stream(CurrenciesEnum.values()).map(Enum::name)
                .collect(Collectors.toList()).containsAll(responsePOJO.getRates().keySet()));
    }

    @Then("^response contains full list of Currency Rates without \"([^\"]*)\"$")
    public void assertRatesLimited(String baseCurrency) {
        List<String> listWithoutBase = Arrays.stream(CurrenciesEnum.values()).map(Enum::name)
                .filter(curr -> !curr.equals(baseCurrency)).collect(Collectors.toList());
        assertTrue(listWithoutBase.containsAll(responsePOJO.getRates().keySet()));
    }

    @Then("response contains latest date")
    public void assertLatestDate() {
        assertEquals(Helper.getLatestDate(), responsePOJO.getDate());
    }

    @Then("^response contains error for invalid Currency Symbol \"([^\"]*)\" for date \"([^\"]*)\"$")
    public void assertInvalidSymbolError(String invalidSymbol, String date) {
        assertEquals(String.format("Symbols '%s' are invalid for date %s.", invalidSymbol, date.isEmpty() ? Helper.getLatestDate(): date),
                errorResponsePOJO.getError());
    }

    @Then("^response contains error for not supported Currency Base \"([^\"]*)\"$")
    public void assertNotSupportedBaseError(String notSupportedBaseSymbol) {
        assertEquals(String.format("Base '%s' is not supported.", notSupportedBaseSymbol), errorResponsePOJO.getError());
    }

    @Then("response contains error `no data for dates older then 1999-01-04`")
    public void assertNotSupportedDateError() {
        assertEquals("There is no data for dates older then 1999-01-04.", errorResponsePOJO.getError());
    }

    @Then("^response contains error for date of wrong format \"([^\"]*)\"$")
    public void assertDateNotMatchFormatError(String wrongFormatDate) {
        String format = "%Y-%m-%d";
        assertEquals(String.format("time data '%s' does not match format '%s'", wrongFormatDate, format), errorResponsePOJO.getError());
    }

    @Then("^response contains error for unconverted data remaining \"([^\"]*)\"$")
    public void assertUnconvertedDataError(String remainder) {
        assertEquals(String.format("unconverted data remains: %s", remainder), errorResponsePOJO.getError());
    }

    @Then("^response contains error for url not found \"([^\"]*)\"$")
    public void assertNotFoundUrlError(String url) {
        assertEquals(String.format("Error: Requested URL /api/%s not found", url), errorResponse);
    }

    @Then("response contains Currency Rates for \"([^\"]*)\"$")
    public void assertRatesInResponse(String expectedCurrencyRates) {
        log.debug(responsePOJO.getRates() + " should contain: " + expectedCurrencyRates);
        assertTrue(responsePOJO.getRates().containsKey(expectedCurrencyRates));
    }

    @Then("response Currency Rates for \"([^\"]*)\" equals \"([^\"]*)\"$")
    public void assertRatesInResponse(String currency, Float expectedRate) {
        assertEquals(responsePOJO.getRates().get(currency), expectedRate);
    }

    @Then("^response contains list of Currency Rates for \"([^\"]*)\"$")
    public void assertFilteredRatesInResponse(String symbols) {
        log.debug(responsePOJO.getRates());
        List<String> symbolsList = Arrays.asList(symbols.split(","));
        log.debug(responsePOJO.getRates().keySet() + " should contain all in: " + symbols);
        assertTrue(symbolsList.containsAll(responsePOJO.getRates().keySet()));
    }

    @Then("^response contains expected response body for date \"([^\"]*)\" and queryParams: `base` \"([^\"]*)\" and `symbols` \"([^\"]*)\"$")
    public void assertResponse(String date, String base, String symbol) {
        assertEquals(JsonFileLoader.getJsonFile(date, base, symbol), responsePOJO);
    }

    //endregion
}
