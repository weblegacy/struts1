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

## MAVEN-Profiles

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