# Struts1 - Reloaded

Is a clone of <https://github.com/apache/struts1.git> - STRUTS_1_3_BRANCH, aiming to bring Struts 1 to a current technology:

* **[Done]**
  * Upgrade MAVEN-Plugins
  * Correct example-apps
  * Complete JUnit5- and integration-test (rewrite old ones)
* **[Open]**
  * JDK 1.4 --> JDK 11
  * Servlet-API 2.3 --> 4.0
  * JSTL 1.0.2 --> 1.2
  * JSP 2.0 --> 2.3
  * MyFaces 1.0.9 --> 2.3
  * Taglibs-Standard 1.0.6 --> 1.2

Full [CHANGELOG](CHANGELOG.md)

## Building Strus1 - Reloaded

### Prerequesits

* Apache Maven 3.5.4\+
* JDK 1.8
* for integration-tests
  * JDK 1.7  
    *Notice:*
    JDK 1.8u91 and greater no longer works with Tomcat 5.x - see also [StackOverflow - The type java.io.ObjectInputStream cannot be resolved. It is indirectly referenced from required .class files](https://stackoverflow.com/questions/36963248/the-type-java-io-objectinputstream-cannot-be-resolved-it-is-indirectly-referenc/38444118).
    Therefore, Tomcat 5.5 must run with a JDK 1.7.
  * Web-Browser:
    * Chrome
    * Firefox
    * Opera
    * Edge
    * Internet Explorer
    * Chromium
    * Safari
  * see also [Integration-Tests README](integration/apps-it-selenium/README.md)
* for documentation
  * JDK 11

### MAVEN-Profiles

* **assembly** - Create assemblies for distribution
  * Adds the module `assembly`
* **pre-assembly** - Creates JavaDoc and Sources for each `struts1`-module
  * `mvn -Ppre-assembly clean package`
* **apps** - Includes the example-apps into build
  * Adds the module `apps`
* **itest** - Includes the integration-tests into build
  * Add the module `integration`
* **release** - Signs all of the project's attached artifacts with GnuPG
* **cargorun** - Starts a web-server to manually test the example-apps
  * `mvn -Papps,itest,cargorun -Dcargo.java.home=[JDK_1.7]`

### Building-Steps

1. Clean full project
   `mvn -Papps,assembly,itest clean`
2. Build and test project
   * with example-apps  
     `mvn -Papps`
   * without example-apps  
     `mvn`
   * to skip tests  
     add `-DskipTests` for example `mvn -Papps -DskipTests`
3. Integration-Tests
   * Run with default-browser (Chrome)  
     `mvn -Papps,itest -Dcargo.java.home=[JDK_1.7]`
   * Run with specific browser  
     `mvn -Papps,itest -Dcargo.java.home=[JDK_1.7] -Dwdm.defaultBrowser=[browser]`
     * Values for `browser`
       * `chrome` - Chrome
       * `firefox` - Firefox
       * `opera` - Opera
       * `edge` - Edge
       * `iexplorer` - Internet Explorer
       * `chromium` - Chromium
       * `safari` - Safari
4. Generate source- and javadoc-artifacts  
   `mvn -Papps,pre-assembly -DskipTests package`
5. Generate documentation  
   `mvn -Papps -DskipTests site`
6. Generate Assemblies  
   `mvn -Papps,assembly -DskipTests package`

### Support runs

* Run Web-Server to manually test example-apps and create test scripts:  
  `mvn -Papps,itest,cargorun -Dcargo.java.home=[JDK_1.7] -DskipTests`
* Set version number  
  `mvn -Papps,itest,assembly versions:set -DnewVersion=...`
* Dependency Report  
  `mvn -Papps,itest,assembly versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`
