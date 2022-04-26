#Project server task
Demo project to test dev skills

## Set up local env
Run a local docker compose file which contains postgresql 14.1, with a proper user/password and db:   
```docker-compose -f docker-compose-local.yml -p local up```

Postgresql user/password and db:

      - POSTGRES_DB=projects-server-task
      - POSTGRES_USER=someUser
      - POSTGRES_PASSWORD=somePassword

##Swagger
To access swagger openIp (3.0) for:
#### admin service: http://localhost:8082/webjars/swagger-ui/index.html
#### public api service: http://localhost:8081/webjars/swagger-ui/index.html




# projects-server-task

Develop a sample of a micro-services server architecture that allows to create projects that contains multi-users. 
<br/>The server should contain two services:
1. API service - Implement CRUD api for prjects and users. 
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
