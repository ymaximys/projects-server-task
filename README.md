#Project server task
Demo project to test dev skills

## Set up local env
Run a local docker compose file which contains postgresql 14.1, with a proper user/password and db:   
```docker-compose -f docker-compose-local.yml -p local up```

Postgresql user/password and db (default public schema is used):

      - POSTGRES_DB=projects-server-task
      - POSTGRES_USER=someUser
      - POSTGRES_PASSWORD=somePassword
      
## Run application from your IDE
- to run API service please open "open-api" module and launch ```PublicApiApplication``` class  
- to run Admin service please open "admin-service-postgresql" module and launch ```ServicePostgresqlApplication``` class 

##Swagger
To access swagger (and openApi 3.0) for:
#### admin service: http://localhost:8082/webjars/swagger-ui/index.html
#### public api service: http://localhost:8081/webjars/swagger-ui/index.html

##DOCKER
To build docker images for microservices please run maven command: ```mvn clean build```, which will build project, 
create docker images and put it on local docker image repository (``` docker images```).

To run microservices using docker:
 -  #### admin service: ```docker run -p 8082:8082 -t admin-service-postgresql:0.0.1-SNAPSHOT```
 -  #### public api service: ```docker run -p 8081:8081 -t public-api:0.0.1-SNAPSHOT```

##Note! 
admin-service-postgresql image will fail because it uses localhost to connect to postgresql db, which obviously not in a container. 
Please set db and flyway hosts as your machine ip in property file (application.yml), re-build project and try again.
Please do the same for public-api service, modify admin.service.base.url property with your machine ip. 


# projects-server-task task description

Develop a sample of a micro-services server architecture that allows to create projects that contains multi-users. 
<br/>The server should contain two services:
1. API service - Implement CRUD api for projects and users. 
2. Admin service - Reponsible for projects and users business logic.

### API Service
An external API server that recieves the requests from the client.
The API you should support:
1. Projects CRUD API: 
  - Project should contain - project name, status (0/1)
2. Users CRUD API:
 - Users should contain - full name, login name, password.
3. Adding/Removing new users to existing projects:
  - Users can be added to multiple projects.

### Admin service 
A micro-service responsible for all the business logic of the application.
<br/>Responsible for saving entities on DB of your choice.

### Bonus 
Use Dockers for servers.

### Bonus for full stack developers
Simple UI in Angular for creation of new projects and adding of users to it.

### Last thing
##### What we anticipate to see in the  app:

1. Write the app with Java and Spring boot (reactive stack: webflux/r2dbc/mongo/etc)
2. Write unit tests
3. Use design patterns that allows flexible code structure, showing anticipation for evolution and new features 
4. Cut corners, but not to the point where the structure vanishes
5. Conventional coding style
6. README file and Reasonable comments (where needed)

# Good luck!
