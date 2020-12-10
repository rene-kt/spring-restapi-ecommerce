# Getting started
First of all, if you're using the application locally:

just use ```localhost:3000/``` instead of ```https://renejr-ecommerce.herokuapp.com/```


This url: **"https://renejr-ecommerce.herokuapp.com/ecommerce"** is depreceted, please, use **"https://renejr-ecommerce.herokuapp.com/"** without **"/ecommerce"** to run 
properly**

### The first step 

You need to create an account, then, acess the endpoint as the image is showing, and do the request. 
 ![image](https://user-images.githubusercontent.com/49681380/101053695-da47b180-3566-11eb-9aa9-b919d6d9ce0f.png)
 
 > Check the body of the request to verify if anything gone wrong.
 
 ### The second step

After you sign up, now you can log in into the system, through the following endpoint 

![image](https://user-images.githubusercontent.com/49681380/101054773-06affd80-3568-11eb-9a94-38f52e042543.png)

> again, check the status code to make sure that everything is alright 

### The third step 

This is the most important step. This system uses Spring Security, and this frameworks denies all endpoints that doesn't have the authorization token in the headers. 
Then, after your login, go and check the headers of the json, and search for Authorization, just like this: 

![image](https://user-images.githubusercontent.com/49681380/101055415-a3729b00-3568-11eb-805d-0077199e933d.png)

Copy it, and saves it in some place. Because, if you don't have acess to it, every endpoints will return a status: 403 DENIED.

### Final step 

Just go into any endpoint, and paste your token, like this: 

![image](https://user-images.githubusercontent.com/49681380/101055883-23990080-3569-11eb-9131-7dcd2043a3b8.png)


**Now, you're perfectly able to run this application with any doubt** 
