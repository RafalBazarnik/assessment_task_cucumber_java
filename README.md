# assessment_task_cucumber_java
##Assessment Task made with Cucumber-JVM and Java - test automation of https://ratesapi.io/documentation/

### Info:
* run with `mvn test`
* in IDE install: Lombok Plugin - https://projectlombok.org/setup/intellij and enable annotation processors
* report in `target\cucumber-reports.html`
* api documentation: https://ratesapi.io/documentation
* api data is based on: https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
(quote: "The reference rates are usually updated around 16:00 CET on every working day, except on TARGET closing days. They are based on a regular daily concertation procedure between central banks across Europe, which normally takes place at 14:15 CET.")
* test cases: https://docs.google.com/spreadsheets/d/1uq59oolIGf1_fbsWvvnvuxx6Fn4sIzkedwi1CTpWsqs/edit?usp=sharing
* postman collection in src\main\resources (Collection v2.1)
* advised plugin: https://plugins.jetbrains.com/plugin/7212-cucumber-for-java (will require additional Gherkin language plugin)
* failing test on purpose under tag: @FailingOnPurpose in GetLatestRate.feature

### Comments:
* date field works in a very peculiar way - data in api is updated "around" 16.00 CET on every working day so on weekends or below 16:00 CET the date is from previous working date
* some requests also appear to have some sort of cache - because even one or two hours after 16:00 CET - \latest request without query param will have previous date but most of the others will contain current day (and if current day is requested using <date> current day rates will be returned)
* my solution is missing exception for TARGET closing day (in Helper.getLatestDate method) - some banking holidays: https://www.ecb.europa.eu/home/contacts/working-hours/html/index.en.html, also because the data is a third party it should be first validated (eg. bank holidays may be added or currency symbols may change)