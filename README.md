# Crypto Mania Api (Backend)
<img src="https://i.ibb.co/Hdq55GW/spring.jpg" alt="spring" align="center" border="0"> 

## Project Summary 
* This  is a Restful Spring boot application with CRUD operations that allow users to create an account to the cryptomania app, save or add crypto coins to a personal watchlist, delete those crypto coins and login and out using spring security.
* It uses  Spring boot with Tomcat server as a framework.
* The app has 9 endpoints namely : /home, /signup, /verifyRegistration, /news, /login, /save, /user, /cryptos and /crypto/id.
* Uses Spring security and Jwt to secure these endpoints.
* Uses spring boot data jpa (hibernate) to persist data to a mysql database.


### **Resources Used**
***
**Java Version**: 18

**Dependencies**: Jwt Token, Hibernate, Spring security, Java Mail, TomcatServer, lombok, mysql-connector, Spring web and Spring webflux.  
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=flat&logo=spring&logoColor=white) 	![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=JSON%20web%20tokens) 	![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=flat&logo=mysql&logoColor=white)

**For Web Framework Requirements**: pom.xml

**APIs**: News Org API

### **EndPoints Building**
***
Built 2 Controllers, one for user account creation and the other for user data interaction.
#### **User Account Creation Endpoints:** 
* **/signup (PostMethod)**: Takes in firstname, lastname, password and email for user signup. A Jwt token is created as an authentication tool, its stored on the database and also sent by java mail to user email for verification. The password is encrypted using BCryptPasswordEncoder.

* **/verifyToken  (GetMethod)**: validates the email token against the one on the database, once verified the account is enabled. 
* **/login  (GetMethod)**: A Jwt token is created and returned if user login credentials are valid. 
* **/news (GetMethod)** : This endpoint calls an external Api https://newsapi.org for latest news articles on cryptocurrencies and returns the data to the client.

#### **User Data Interaction Endpoints:**  
* **/save (PostMethod)**:  saves user crypto coins to the database with all the coin's features like marketCap, alltime highs, rank and number of coins in circulation. 
* **/cryptos (GetMethod)**:  retrieves all the saved cryptos of the client from the database.
* **/crypto/id (DeleteMethod)** : deletes a specific crypto currency by id from the database.

### **Data Storage**
Used Spring data JPA (Hibernate) to persist and retrieve data from a mysql database.  
Built 3 entities: 
* User Entity to store app users.
* VerificationToken Entity to store signup verification tokens.
* CryptoData Entity to store crypto currencies of users. 



### **Productionization**
***
In this step I deployed the mysql database to AWS and deployed the springboot app to Heroku cloud.

**Live Implemantation:** [CryptoMania](react-cryptomania.herokuapp.com)
