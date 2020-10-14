# Kafka Demo 

## Description
Small Spring application created in order to learn more about Apache Kafka as part of my LnD within my role.


## Set Up
Editor Used : IntelliJ IDEA

Fork the repository and copy the URL. Navigate into your desktop from your terminal and clone the repository locally

* git clone [URL] 

Once you open the local directory on the text editor build the project localy with Gradle

To run the project localy, on your terminal direct to the project file

* cd ~/src/deployment

and run 

* docker-compose -f docker-compose.yaml up 

Ensure that all the right containers are up with 

* docker-compose ps 
and/or 
* docker ps

Ensure that the application is running.

There are four available endpoints:

* GET /users : Returns a list of all the users. Optional name parameter.
* GET /user/{id} :  Returns the user with the requested id.
* POST /user :  Adds a user to the db
* PUT /user/{id}/address : Edit the address of a user.
