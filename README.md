##

## RESTAURANT WEB APP

System Restaurant. Customer makes an order from the Menu.
Administrator confirms the Order and sends it to the kitchen for
execution. Administrator provides an invoice. Customer effects the payment of an invoice.

##

### Technologies

- Java 8
- Servlets, JSP, JSTL
- JDBC
- MySQL
- JUnit, DbUnit, Mockito
- Log4j
- HTML, CSS, JS

##

### Installation

##

#### Pre requirements
- jdk 1.8
- Maven
- Tomcat 9
- MySQL Server

##

#### Steps

1. Clone or download project
2. Create new schema(with any name) 
3. In project's "META-INF/context.xml" specify url to your schema, username and password
4. Import tables and data to your schema from resources/database/dump.sql
5. Create folder where loaded images will be stored and specify path to the folder in "resources/image/image.properties" 
6. Copy content from "resources/image/init" to previously created folder 
7. Package project with maven
8. Put "restaurant.war" in "/webapps" folder of installed(and run) tomcat
9. Go to "localhost:xxxx/restaurant"

##