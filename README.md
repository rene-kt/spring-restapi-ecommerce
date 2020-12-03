# Spring rest API ecommerce :coffee:



## Technologies :computer:

- Java
- Spring boot
- Spring Data JPA 
- Spring security 
- JWT
- SMTP Email service
- Postman
- Eclipse 
- Swagger 
- PostgreSQL
- Heroku (to deploy the application) 

## How to use :wave: (IF YOU WANT TO USE IT LOCALLY) 

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

  **Now, you are able to run this Java application locally.** :heavy_check_mark:


## If you only want to use the API without cloning the project 

- Open a software like postman to do the requests
- Acess the endpoints through  https://renejr-ecommerce.herokuapp.com/ecommerce

## Usage of the application 

In this [document](usage.md), there are some steps that you must to follow to use this application. I recommend to take a read before
start using it.

# API Documentation :memo:

Swagger is responsible to provide a documentation of the API, it break down the endpoints and the models of the application.
Check it out:  https://renejr-ecommerce.herokuapp.com/swagger-ui.html 

![swagger](https://user-images.githubusercontent.com/49681380/101045417-065f3480-355f-11eb-8899-c9fe12e174cc.png)



## How to contribute :question:

1. Make a fork;
2. Create a branch with your feature: `git checkout -b my-feature`;
3. Commit changes: `git commit -m 'Creating new classes'`;
4. Push the changes: `git push origin my-feature`.





