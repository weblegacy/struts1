# Struts1 - Reloaded

Is a clone of <https://github.com/apache/struts1.git> - Branche `trunk`, aiming to bring Struts 1 to a current technology:

Full [CHANGELOG](CHANGELOG.md)

For documentation see [https://weblegacy.github.io/struts1](https://weblegacy.github.io/struts1)

## Versions-Overview

| Version                                                                   | JEE-Version  | Java-Version | Servlet | JSP | EL  | JSF | JSTL |
|--------------------------------------------------------------------------:|-------------:|-------------:|--------:|----:|----:|----:|-----:|
| [1.5.0-RC1](https://github.com/weblegacy/struts1/releases/tag/v1.5.0_rc1) | Jakarta EE 9 |            8 |     5.0 | 3.0 | 4.0 | 3.0 |  2.0 |
|         [1.4.5](https://github.com/weblegacy/struts1/releases/tag/v1.4.5) | Jakarta EE 8 |            8 |     4.0 | 2.3 | 3.0 | 2.3 |  1.2 |
|         [1.4.4](https://github.com/weblegacy/struts1/releases/tag/v1.4.4) | Jakarta EE 8 |            8 |     4.0 | 2.3 | 3.0 | 2.3 |  1.2 |
|         [1.4.3](https://github.com/weblegacy/struts1/releases/tag/v1.4.3) |    Java EE 7 |            8 |     3.1 | 2.3 | 3.0 | 2.2 |  1.2 |
|         [1.4.2](https://github.com/weblegacy/struts1/releases/tag/v1.4.2) |    Java EE 6 |            8 |     3.0 | 2.2 | 2.2 | 2.0 |  1.2 |
|         [1.4.1](https://github.com/weblegacy/struts1/releases/tag/v1.4.1) |    Java EE 5 |            8 |     2.5 | 2.1 | 2.1 | 1.2 |  1.2 |
|         [1.4.0](https://github.com/weblegacy/struts1/releases/tag/v1.4.0) |     J2EE 1.4 |          1.4 |     2.3 | 2.0 | 2.0 | 1.0 |  1.0 |
|       [1.3.11](https://github.com/weblegacy/struts1/releases/tag/v1.3.11) |     J2EE 1.4 |          1.4 |     2.3 | 2.0 | 2.0 | 1.0 |  1.0 |

### Current Roadmap

* 1.5.0
  * Include missing taglib docs, because the current version of [tlddoc](https://github.com/weblegacy/tlddoc) is not able to generate it (upgrade to `Jakarta Server Pages 3.0` is currently missing).
* 1.5.1
  * [#23 - Apache Tiles: Unvalidated input may lead to path traversal and XXE](https://github.com/weblegacy/struts1/security/dependabot/23)
  * Bump `commons-fileupload2` from 2.0.0-M1 to 2.0.0-M2
* 1.4.6
  * Cherry-Pick relevant changes from version `1.5.0` and `1.5.1`
* 1.6.0
  * Bump JDK 8 to 11
  * Upgrade to Jakarta EE 10

## Changes since version 1.3.10

* [#11 - XML Entities not handled correctly](https://github.com/weblegacy/struts1/issues/11)
* Include all open patches from apache-struts1-repo
* Fixed vulnerabilities
* Upgrade MAVEN-Plugins
* Correct example-apps
* Complete JUnit5- and integration-test (rewrite old ones)
* Logging: Use [SLF4J](https://www.slf4j.org/) instead [Commons-Logging](https://commons.apache.org/proper/commons-logging/index.html)
* JDK 1.4 --> JDK 8
  * Tested with JDK 8, 11, 17 and 21
* Servlet-API 2.3 --> 5.0
* JSP 2.0 --> 3.0
* JSP-EL 2.0 --> 4.0
* JSF 1.0.9 --> 3.0
* JSTL 1.0.2 --> 2.0

## Fixed vulnerabilities

* [CVE-2008-2025 - Apache Struts Cross-site Scripting vulnerability](https://github.com/advisories/GHSA-wcgx-2hvx-5cwr)
* [CVE-2012-1007 - Apache Struts XSS](https://github.com/advisories/GHSA-9848-v244-962p)
* [CVE-2014-0114 - Arbitrary code execution in Apache Commons BeanUtils](https://github.com/advisories/GHSA-p66x-2cv9-qq3v)
* [CVE-2015-0899 - Improper Input Validation in Apache Struts](https://github.com/advisories/GHSA-cvvx-r33m-v7pq)
* [CVE-2016-1181 - Improper Input Validation in Apache Struts](https://github.com/advisories/GHSA-7jw3-5q4w-89qg)
* [CVE-2016-1182 - Improper Input Validation in Apache Struts](https://github.com/advisories/GHSA-5ggr-mpgw-3mgx)
* [CVE-2023-34396 - Apache Struts vulnerable to memory exhaustion](https://github.com/advisories/GHSA-4g42-gqrg-4633)
* [CVE-2023-49735 - Apache Tiles: Unvalidated input may lead to path traversal and XXE](https://github.com/advisories/GHSA-qw4h-3xjj-84cc)

## Building Strus1 - Reloaded

### Prerequisites

* Apache Maven 3.8.1\+
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
