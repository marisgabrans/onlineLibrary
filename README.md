# Online Library - Spring Boot web app
Online Library is a web app which was created as a team project Accenture Java Bootcamp. \

## Built With
App is written in Java and works with MySQL database. Front-end built with Thymeleaf and Bootstrap.

## Functionality
Web app is secured and in order to log in users are prompted to create accounts. Currently app supports two roles: `ROLE_USER` and `ROLE_ADMIN`. 
* User can view available book list and make book reservations (an email confirmation is sent out). 
* Admin has access to all website pages and can crate new or view/update/delete existing records.


# Getting started
## Prerequisites
To launch the app you will need preinstalled:
* JDK 11
* Maven 3
* MySQL Server

## Installation and usage
1. Create a database `library`. Tables will be created automatically on the first launch of the application
2. Clone the repo: https://github.com/GuntarsK/online-library.git
3. Edit `application.properties` file - set your credentials for database connection and email account
4. Build jar with IDE or via commandline: `$ mvn package`
5. Run the application `$ java -jar target/${project.build.finalName}.jar`
6. Access application in web browser: http://localhost:8080
7. Create a new account by registering in the web app. All users by default have role `ROLE_USER` and have limited access to web app functionalities. Update user role to `ROLE_USER` in database table `role` to get access to all web app features.


## Licence
Distributed under the MIT License. See LICENSE for more information.
