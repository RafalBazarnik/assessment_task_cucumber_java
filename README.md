# assessment_task_cucumber_java
##Assessment Task made with Cucumber-JVM and Java - test automation of https://ratesapi.io/documentation/

### Info:
* run with `mvn test`
* report in `target\cucumber-reports.html`
* Api documentation: https://ratesapi.io/documentation/
Data based on: https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
("The reference rates are usually updated around 16:00 CET on every working day, except on TARGET closing days. They are based on a regular daily concertation procedure between central banks across Europe, which normally takes place at 14:15 CET.")
* przypadki testowe: https://docs.google.com/spreadsheets/d/1uq59oolIGf1_fbsWvvnvuxx6Fn4sIzkedwi1CTpWsqs/edit?usp=sharing
* Postman collection in src\main\resources (Collection v2.1)
* advised plugin: https://plugins.jetbrains.com/plugin/7212-cucumber-for-java (will require additional Gherkin language plugin)

### Comments:
* date field works in a very peculiar way - every day till 16:00 CET all dates for \latest should be a previous day, but after that point \latest request without query param will have previous date but most of the others will contain current day (and if current day is requested using \<date> current day rates will be returned)
* above bug? will serve as the failing test example for now (the dates are probably cached somehow - because at night the dates are the same)
* https://api.ratesapi.io/api/2020-01-01?base=USD&symbols=GBP,PLN will return date for "date": "2019-12-31"
* date: https://api.ratesapi.io/api/2020-08-1 will return "date": "2020-07-31"