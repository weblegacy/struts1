# Struts1 - Reloaded

Is a clone of <https://github.com/apache/struts1.git> - Branche `trunk`, aiming to bring Struts 1 to a current technology:

Full [CHANGELOG](CHANGELOG.md)

For documentation see [https://weblegacy.github.io/struts1](https://weblegacy.github.io/struts1)

## Versions-Overview

| Version                                                             | JEE-Version  | Java-Version | Servlet | JSP | EL  | JSF | JSTL |
|--------------------------------------------------------------------:|-------------:|-------------:|--------:|----:|----:|----:|-----:|
|                                                         1.4.5 (WiP) | Jakarta EE 9 |            8 |     5.0 | 3.0 | 4.0 | 3.0 |  2.0 |
|   [1.4.4](https://github.com/weblegacy/struts1/releases/tag/v1.4.4) | Jakarta EE 8 |            8 |     4.0 | 2.3 | 3.0 | 2.3 |  1.2 |
|   [1.4.3](https://github.com/weblegacy/struts1/releases/tag/v1.4.3) |    Java EE 7 |            8 |     3.1 | 2.3 | 3.0 | 2.2 |  1.2 |
|   [1.4.2](https://github.com/weblegacy/struts1/releases/tag/v1.4.2) |    Java EE 6 |            8 |     3.0 | 2.2 | 2.2 | 2.0 |  1.2 |
|   [1.4.1](https://github.com/weblegacy/struts1/releases/tag/v1.4.1) |    Java EE 5 |            8 |     2.5 | 2.1 | 2.1 | 1.2 |  1.2 |
|   [1.4.0](https://github.com/weblegacy/struts1/releases/tag/v1.4.0) |     J2EE 1.4 |          1.4 |     2.3 | 2.0 | 2.0 | 1.0 |  1.0 |
| [1.3.11](https://github.com/weblegacy/struts1/releases/tag/v1.3.11) |     J2EE 1.4 |          1.4 |     2.3 | 2.0 | 2.0 | 1.0 |  1.0 |

## Changes since version 1.3.10

* Include all open patches from apache-struts1-repo
* Fixed vulnerabilities
* Upgrade MAVEN-Plugins
* Correct example-apps
* Complete JUnit5- and integration-test (rewrite old ones)
* Logging: Use [SLF4J](https://www.slf4j.org/) instead [Commons-Logging](https://commons.apache.org/proper/commons-logging/index.html)
* JDK 1.4 --> JDK 8
  * Tested with JDK 8, 11, 17 and 19
* Servlet-API 2.3 --> 4.0
* JSP 2.0 --> 2.3
* JSP-EL 2.0 --> 3.0
* JSF 1.0.9 --> 2.3
* JSTL 1.0.2 --> 1.2

## Fixed vulnerabilities

* CVE-2014-0114
* CVE-2015-0899
* CVE-2016-1181
* CVE-2016-1182

## Building Strus1 - Reloaded

### Prerequesits

* Apache Maven 3.5.4\+
* JDK 11\+
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
  * `mvn -Pdormant,apps,itest,cargorun`
  * `mvn -Pdormant,apps,itest,cargorun -Dcargo.java.home=[JDK_x]` to specify Java-Runtime

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
  or  
  `mvn -Pdormant,apps,itest,cargorun -DskipTests -Dcargo.java.home=[JDK_x]` to specify Java-Runtime
* Set version number  
  `mvn -Pdormant,apps,itest,assembly versions:set -DnewVersion=...`
* Dependency Report  
  `mvn -Pdormant,apps,itest,assembly versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`
