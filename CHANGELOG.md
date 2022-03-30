# 1.3.11 / YYYY-MM-DD

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