# 1.3.11 / YYYY-MM-DD

* Correct some URLs in site-doc-files
* Some reformatings in site-doc-files
* Add XSD to all site-doc-files and correct them
* Add/update XML-Declaration with encoding
* Adapt internal links in site-docs
* Delete not used `navigation.xml`'s from FAQs and user-guide
* Add again deleted preface to user-guide
* Downgrade `checkstyle` from 10.2 to 9.3 to use it with Java 8
* Correct http-adress of the license
* Use new `taglib-maven-plugin` from `io.github.weblegacy`
* Bump `webdrivermanager` from 5.1.0 to 5.1.1
* Bump `htmlunit` from 2.60.0 to 2.61.0
* Bump `cargo-maven3-plugin` from 1.9.10 to 1.9.11
* Bump `maven-site-plugin` from 3.11.0 to 3.12.0
* Bump `maven-javadoc-plugin` from 3.3.2 to 3.4.0
* Bump `checkstyle` from 10.1 to 10.2
* Use `commons-io` 2.11.0 for integration-tests
* Add more informations into `README`s, remove `mvn_hints`
* Bump `maven-taglib-plugin` 2.4 to `taglib-maven-plugin` 2.5
* Generate Assemblies in package-phase
* apps/faces-examples: remove spaces in comments
* Add optional dependency `commons-io`
* Bump MAVEN-Plugins (clean, failsafe, surefire, surefire-report)
* STR-2802: Add a test case
* STR-2321 and STR-2319: Compare floats and doubles
* Update README
* Reorg integration-tests
* Correct `html:link`-Tag: params connectect with `&amp;` instead `&`
* apps/[scripting-]mailreader: Convert `Canoo WebTests` to `Selenium IDE`-tests
* apps/[scripting-]mailreader: Add missing gif and remove version from index-title
* Remove unnecessary taglib-definitions in `faces-example`s
* Correct `faces-example`s (wrong DOCTYPEs) and activate Struts-Faces-Examples again
* Remove unused import
* Some corrections in `src/site`-docs
* WW-1974 Add link to release notes form announcement. Correct link in archival copy of 1.2.7 release notes
* STR-2153: Reveal the offending bean and property name when iterating
* STR-1195: SelectTag should single select when "multiple" attribute equals false
* STR-3191: Filter attributes
* STR-2779: Better explain why invalid collection (thanks to Ralf Hauser)
* STR-2904: Verify only one instance of the plugin is loaded per module
* STR-2611: Cite the form name in validation exception message
* Reorder language specific ActionResources-properties
* STR-1809: Enhance log error message for when the Action cannot instantiate
* STR-3085: Configuration post-processing via plug-ins
* STR-1819: Warn if a form is not declared but later used
* Skip install- and deployment-phase for integration tests
* Move more property files into standard Maven resources folder
* Correct `html-img.jsp` in `apps/examples`
* STR-2700: Correction - Clear input stream on aborted upload (Windows only)
* Adjust resource definitions in pom.xml
* STR-2013: Add Spanish translations
* Move property files into standard Maven resources folder
* Remove NOTICE.txt from Apache Struts EL
* STR-3042: Retrieve page through methods
* STR-2098: Clarify FileNotFoundException depends on underlying implementation
* STR-2959: File upload has 2GB limit
* STR-2700: Clear input stream on aborted upload (Windows only)
* STR-3068: Warn when using the classic RequestProcessor
* STR-1870: Add debug log message to explain initialization strategy
* STR-1870: Fail fast when definition is duplicated
* STR-2924: Allow Exception key to be optional
* STR-2020: Explain that NPE is caused by missing ActionServlet
* STR-1638: Note that anchor cannot be standalone
* STR-3150: Use Chain processing constants
* STR-3119: Add root cause to thrown exceptions that do not accept root cause in the constructor
* STR-2437: Standardize exception rethrows to contain root cause
* Some test-code reformating, no change
* Core: Making some of the fail messages a bit more expressive
* Bump selenium-java from 4.1.2 to 4.1.3
* Bump htmlunit from 2.59.0 to 2.60.0
* Bump maven-jxr-plugin from 3.1.1 to 3.2.0
* Bump checkstyle from 10.0 to 10.1
* Continue processing on `JavaDoc`-error in profile `pre-assembly`
* Centralize profile `pre-assembly`
* STR-3121: Bump Commons Logging to 1.1.1
* STR-3059: Bump Commons FileUpload to 1.2.1
* STR-3152: Pull common dependencies into the parent Struts 1 POM
* IT: Add `refresh` to Selenium-Test
* IT: Update `README`
* IT: Add `WebDriverManager` and activate Selenium-Test
* IT: Correct Selenium-Test and add configurable port-number
* IT: Add methods for JDK 1.7-compatibility
* Convert `testgen4web` to `Selenium` integration tests
* Add `cargorun`-profile
* Add forgotten `dispatchValidator.zip` from apache-struts1-clone
* Integration-test: skip creating empty jar file
* Move integration-test-settings to integration-test-parent-POM
* Integration-tests with JUnit5 and deploy server with cargo-function
* Bump JUnit 3.8.1 to JUnit5 5.8.2
* Compile tests with JDK 1.8 and integration-tests with JDK 1.7
* Move `TestMockBase` in module `core` from main into test
* Centralize junit dependency
* Update ReadMe
* Update javadoc-links
* Change comment in checkstyle-config
* Update deprecated MAVEN-Var ${basedir} to ${project.basedir}
* Move maven-war-plugin from struts-app to parent
* Set current MAVEN-Plugins in subproject-profile
* Bump MAVEN-Plugins to current versions
* Upgrade Checkstyle to 10.0
* Move checkstyle-config to local
* Upgrade maven-assembly-plugin with adjustments to current version
* Disable 2 non-working integration-tests
* Upgrade cargo-maven2-plugin (with dependencies) to cargo-maven3-plugin
* Replace maven-antrun-plugin with maven-resources-plugin
* Add/Define missing maven-plugins
* Define Java-Version thru MAVEN-Var
* Add maven-resources-plugin: Define encoding
* Add maven-enforcer-plugin: Enforces required MAVEN-Version
* Update deprecated MAVEN-Var ${version}
* Centralize MAVAN-Plugins-Version with PluginManagement
* Replace org.apache.struts with ${project.groupId}
* Remove MAVEN-GroupID from Sub-Projects
* Remove MAVEN-Default-Plugin-Group (org.apache.maven.plugins)

# 1.3.11-SNAPSHOT / 2022-03-11

* Clone of <https://github.com/apache/struts1.git> - STRUTS_1_3_BRANCH
* Add CHANGELOG
* Add LICENSE
* Add mvn_hints.md
* Add NOTICE
* Add README.md