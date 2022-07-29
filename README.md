# Struts1 - Reloaded

Is a clone of <https://github.com/apache/struts1.git> - Branche `trunk`, aiming to bring Struts 1 to a current technology:

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

For documentation see [https://weblegacy.github.io/struts1](https://weblegacy.github.io/struts1)

## Fixed vulnerabilities

- CVE-2014-0114

## Building Strus1 - Reloaded

### Prerequesits

* Apache Maven 3.5.4\+
* JDK 1.8
* for integration-tests
  * Web-Browser:
    * Chrome
    * Firefox
    * Opera
    * Edge
    * Internet Explorer
    * Chromium
    * Safari
  * see also [Integration-Tests README](integration/apps-it-selenium/README.md)

### MAVEN-Profiles

* **dormant** - Dormant sub-projects
  * Adds the dormant sub-projects `Faces` and `EL` to the build-process
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
   `mvn -Pdormant,apps,assembly,itest clean`
2. Build and test project
   * with example-apps  
     `mvn -Pdormant,apps`
   * without example-apps  
     `mvn -Pdormant`
   * to skip tests  
     add `-DskipTests` for example `mvn -Pdormant,apps -DskipTests`
3. Integration-Tests
   * Run with default-browser (Chrome)  
     `mvn -Pdormant,apps,itest`
   * Run with specific browser  
     `mvn -Pdormant,apps,itest -Dwdm.defaultBrowser=[browser]`
     * Values for `browser`
       * `chrome` - Chrome
       * `firefox` - Firefox
       * `opera` - Opera
       * `edge` - Edge
       * `iexplorer` - Internet Explorer
       * `chromium` - Chromium
       * `safari` - Safari
4. Generate source- and javadoc-artifacts  
   `mvn -Pdormant,apps,pre-assembly -DskipTests package`
5. Generate site-documentation  
   `mvn -Pdormant,apps -DskipTests site`  
   or  
   `mvn -Pdormant,apps -DskipTests clean site site:stage`
6. Publish site-documentation  
   1. `mvn -Pdormant,apps -DskipTests clean site site:stage`
   2. `mvn scm-publish:publish-scm`
7. Generate Assemblies  
   `mvn -Pdormant,apps,assembly -DskipTests package`
8. Deploy all artifacts to `Central-Repo`  
   * `mvn -Pdormant clean deploy` for SNAPSHOTs
   * `mvn -Pdormant,pre-assembly,release clean deploy` for releases

### Support runs

* Run Web-Server to manually test example-apps and create test scripts:  
  `mvn -Pdormant,apps,itest,cargorun -DskipTests`
* Set version number  
  `mvn -Pdormant,apps,itest,assembly versions:set -DnewVersion=...`
* Dependency Report  
  `mvn -Pdormant,apps,itest,assembly versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`
