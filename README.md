# Spring rest API ecommerce :coffee:



## Technologies :computer:

- Java
- Spring boot
- Spring Data JPA 
- Postman
- Eclipse 
- Swagger 
- PostgreSQL
- Heroku (to deploy the application) 

## How to use :wave:

To clone and run this application by yourself, make sure you have at least Java 8 and all JDK stuff (including JRE), Maven to build the dependencies,
Ecplise or STS, and Postman (it's not necessary, though it's really useful to handle a rest API. After that, do the following instructions

- Clone this repository
```bash
$ git clone https://github.com/reness0/spring-restapi-ecommerce
```
- Open this project using Eclipse or Spring tool Suit

- Run ```EcommerceApplication.java```
  > This is gonna be building the maven dependencies too

- The endpoints are located on 'http://localhost:3000/ecommerce'
  > Use a software like postman to do the resquests. 
  
- Make sure to create a database called **ecommerce** 
  > or create it with another name. However, you must to rename its name on ```application.properties```

 


 **By the way, you can change the port (3000) to another one, just change the line on ``` application.properties ```**


# API Documentation :memo:

Swagger is responsible to provide a documentation of the API, it break down the endpoints and the models of the application.
Check it out:  https://renejr-ecommerce.herokuapp.com/swagger-ui.html 

![swagger](https://user-images.githubusercontent.com/49681380/101045417-065f3480-355f-11eb-8899-c9fe12e174cc.png)


**Now, you are able to run this Java application locally.** :heavy_check_mark:

## How to contribute :question:

1. Make a fork;
2. Create a branch with your feature: `git checkout -b my-feature`;
3. Commit changes: `git commit -m 'Creating new classes'`;
4. Push the changes: `git push origin my-feature`.





