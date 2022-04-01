# 1.3.11 / YYYY-MM-DD

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