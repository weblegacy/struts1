# MAVEN-Hints
## Dependency Report
`mvn -Papps,itest,assembly,release versions:display-dependency-updates versions:display-plugin-updates versions:display-property-updates`

## Install
`mvn -Papps,itest -Dcargo.tomcat5x.home=[HOME Tomcat5x] -Dcargo.java.home=[HOME JDK 1.7] clean install`

*Notice:*
JDK 1.8u91 and greater no longer works with Tomcat 5.x  - see also [StackOverflow - The type java.io.ObjectInputStream cannot be resolved. It is indirectly referenced from required .class files](https://stackoverflow.com/questions/36963248/the-type-java-io-objectinputstream-cannot-be-resolved-it-is-indirectly-referenc/38444118).

Therefore, Tomcat 5.5 must run with a JDK 1.7.

**Example:**  
`mvn -Papps,itest -Dcargo.tomcat5x.home=c:/EProg/apache-tomcat-5.5.36 -Dcargo.java.home=c:/Program Files (x86)/Java/jdk1.7.0_80 clean install`

## Create JavaDoc
`mvn -Papps,itest,assembly site`

## Assembly
`assembly/mvn -Papps,itest,assembly clean package assembly:single`

## Set version number
`mvn -Papps,itest,assembly,release versions:set -DnewVersion=...`