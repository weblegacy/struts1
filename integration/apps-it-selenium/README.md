# Create Selenium IDE-Integration-Tests

## Create Selenium-Tests

### Prerequisites

* [Selenium IDE](https://www.selenium.dev/selenium-ide/)

### Convert `Selenium-IDE`-Scripts to `JUnit5`-Tests

* Record Selenium-Tests with `Selenium IDE`
* Export with `Java JUnit`
* Rename exported Java-Class from `...Test` to `IT...`, `...IT` or `...ITCase`
* Convert exported Java-Class to JUnit5
  * [Migrating from JUnit 4](https://junit.org/junit5/docs/current/user-guide/#migrating-from-junit4)
  * `@Before` to `@BeforeEach`
  * `@After` to `@AfterEach`
  * Adapt `assert`s
  * Change line `driver = new FirefoxDriver();` to `driver = WebDriverManager.getInstance().create();` in method `setUp`

## MAVEN-Run

### Run with default-browser (Chrome)

`mvn -Pdormant,apps,itest verify`

### Run with specific browser

`mvn -Pdormant,apps,itest -Dwdm.defaultBrowser=[browser] verify`

* Values for `browser`
  * `chrome` - Chrome
  * `firefox` - Firefox
  * `opera` - Opera
  * `edge` - Edge
  * `iexplorer` - Internet Explorer
  * `chromium` - Chromium
  * `safari` - Safari
