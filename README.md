##

## RESTAURANT WEB APP

Task 21. System Restaurant. Customer makes an order from the Menu.
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

1. Clone or download the project
2. Create database(with any name) 
3. In project's "META-INF/context.xml" specify your database url, username and password
4. Import data to your database from resources/database/dump.sql
5. Create folder where loaded images will be stored
6. Copy content from "resources/image/init" to previously created folder 
7. Package project with maven
8. Put "restaurant.war" in "/webapps" folder of installed(and run) tomcat
9. Go to "localhost:xxxx/restaurant"