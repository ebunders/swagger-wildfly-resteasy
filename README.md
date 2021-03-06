swagger-wildfly-resteasy
========================

Example project to show how Swagger Api Doc will be integrated with Wildfly and RESTEasy.

The configuration is done with annotations, it's not web.xml required.


## The example project

The example project is a Java EE 7 JaxRS Application. Its contained a simple echo service
with two service functions.

- echo (The message entered will be given as echo)
 
![Echo Swagger Api Doc](https://github.com/K0NRAD/swagger-wildfly-resteasy/raw/master/doc/resources/swagger-wildfly-resteasy-002.png)

- reverse echo (The message entered will be given as reverse echo)

![Reverse echo Swagger Api Doc](https://github.com/K0NRAD/swagger-wildfly-resteasy/raw/master/doc/resources/swagger-wildfly-resteasy-001.png)


## Wildfly profile

The example is tested on Wildfly 8.0.0.Final with standalone profile.   

## Special thanks to

- Wordnik for the great [Swagger tool](https://github.com/wordnik)
- Adam Bean for the [Java EE 7 maven archetype] (https://github.com/AdamBien/javaee7-essentials-archetype)
- Jen-Ming Chung for the Swagger / Java EE example [Swagger 4 Java EE](https://github.com/jmchung/swagger4javaee)
 


