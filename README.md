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

## Features :fire: 

#### to both users: 
- Sign up and Sign in
- Update and Delete (if you haven't bought or sold any product) yourself
- Get a seller and client ranking 


#### only for clients
- Add product in your wishlist
- Buy product (when this happens, an email is sent to client and to the seller) 

#### only for sellers
- Create, update and delete products
- Sell a product


## How to use :wave: (IF YOU WANT TO USE IT LOCALLY) 

To clone and run this application by yourself, make sure you have at least Java 8 and all JDK stuff (including JRE), Maven to build the dependencies,
Ecplise or STS, and Postman (it's not necessary, though it's really useful to handle a rest API. After that, do the following instructions

- Switch the branch to ```running-locally```

- Clone this repository
```bash
$ git clone https://github.com/reness0/spring-restapi-ecommerce
```
- Open this project using Eclipse or Spring tool Suit

- Run ```EcommerceApplication.java```
  > This is gonna be building the maven dependencies too

- The endpoints are located in 'http://localhost:3000/' and config its port on ```src/main/resources/application.properties```
  > Use a software like postman to do the resquests. 
  
- Make sure to create a database called **ecommerce** 
  > or create it with another name. However, you must to rename its name in ```src/main/resources/application.properties```

 **By the way, you can change the port (3000) to another one, just change the line in ```src/main/resources/application.properties```**

  **Now, you are able to run this Java application locally.** :heavy_check_mark:


## If you only want to use the API without cloning the project 

- Open a software like postman to do the requests
- Acess the endpoints through  https://renejr-ecommerce.herokuapp.com/

## Usage of the application 

In this [document](usage.md), there are some steps that you must to follow to use this application. I recommend to take a read before
start using it.

# API Documentation :memo:

Swagger is responsible to provide a documentation of the API, it break down the endpoints and the models of the application.
Check it out:  https://renejr-ecommerce.herokuapp.com/swagger-ui.html 

![image](https://user-images.githubusercontent.com/49681380/101172029-5e5d7000-361f-11eb-8071-0a6a205a1713.png)


## How to contribute :question:

1. Make a fork;
2. Create a branch with your feature: `git checkout -b my-feature`;
3. Commit changes: `git commit -m 'Creating new classes'`;
4. Push the changes: `git push origin my-feature`.





