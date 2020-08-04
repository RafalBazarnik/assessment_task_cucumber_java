package ratesapi;

import static org.hamcrest.Matchers.containsInAnyOrder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyData;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


public class GetLatestRate {
    private static Logger LOGGER = LogManager.getLogger(GetLatestRate.class);
    private String ENDPOINT = PropertiesLoader.get("basic_api_url");
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;
    private ResponsePOJO responsePOJO;

    // Given:

    @Given("I set GET rates API endpoint")
    public void setEndpoint() {
        RestAssured.baseURI = ENDPOINT;
        request = given();
    }

    @Given("^I use query param `symbols` \"([^\"]*)\"$")
    public void addSymbolsParam(String symbol) {
        request.given().queryParam("symbols", symbol);
    }

    @Given("^I use query param `base` \"([^\"]*)\"$")
    public void addBaseParam(String base) {
        request.given().queryParam("base", base);
    }

    // When:

    @When("^I request url \"([^\"]*)\"$")
    public void requestUrl(String urlPart) {
        response = request.when().get(urlPart);
    }

    //Then:

    @Then("^I get status \"([^\"]*)\"$")
    public void assertStatusCode(String expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(Integer.parseInt(expectedStatusCode), actualStatusCode);
    }

    @Then("^response contains base currency \"([^\"]*)\"$")
    public void assertBaseCurrency(String expectedBaseCurrency) {
        responsePOJO = MapJsonToPOJO.map(response.body().asString());
        assertEquals(expectedBaseCurrency, responsePOJO.getBase());
    }

    @Then("^response contains full list of currency rates without \"([^\"]*)\"$")
    public void assertRates(String baseCurrency) {
        List<String> listWithoutBase = CurrenciesEnum.getFullCurrenciesList()
                .stream().filter(curr -> !curr.equals(baseCurrency)).collect(Collectors.toList());
        assertTrue(listWithoutBase.containsAll(responsePOJO.getRates().keySet()));
    }

    @Then("response contains current date")
    public void assertDate() {
            assertEquals(Helper.getCurrentDate(), responsePOJO.getDate());
    }

    @Then("^response contains error for invalid symbol \"([^\"]*)\"$")
    public void assertInvalidSymbolError(String invalidSymbol) {
        String responseWithError = response.body().asString();
        assertEquals(String.format("{\"error\":\"Symbols '%s' are invalid for date %s.\"}", invalidSymbol, Helper.getCurrentDate()), responseWithError);
    }

    @Then("^response contains error for not supported base \"([^\"]*)\"$")
    public void assertNotSupportedBaseError(String notSupportedBaseSymbol) {
        String responseWithError = response.body().asString();
        assertEquals(String.format("{\"error\":\"Base '%s' is not supported.\"}", notSupportedBaseSymbol), responseWithError);
    }

    @Then("response contains rates for \"([^\"]*)\"$")
    public void assertRatesInResponse(String expectedCurrencyRates) {
        LOGGER.debug(responsePOJO.getRates() + " should contain: " + expectedCurrencyRates);
        assertTrue(responsePOJO.getRates().containsKey(expectedCurrencyRates));
    }

    @Then("response rates for \"([^\"]*)\" equals \"([^\"]*)\"$")
    public void assertRatesInResponse(String currency, Float expectedRate) {
        assertEquals(responsePOJO.getRates().get(currency), expectedRate);
    }
}
