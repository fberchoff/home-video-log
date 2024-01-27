
# Home Video Log

A hobby of some is to organize their home videos so that they can easily find people, places, and scenes to use for their editing projects.

This API allows for storing and retrieving this information using an organized database. A user can define one or more projects and manage the assets under each project.




## Features

- Implements Spring Security for controlling access using JSON Web Tokens (JWT)
- Uses the springdoc-openapi library to implement OpenApi documentation with Swagger UI.
- Using the Swagger UI inteface, you can interact with all the available endpoints in a guided fashion.
- Allows for two types of user roles -ADMIN and USER.  
- An ADMIN manages common data that can be used by any user for any project.
- A USER can create one or more projects and manage the assets under each of their own projects.
- An ADMIN can manage all aspects of any user's project.


## Installation

The project runs under Spring Boot within your IDE.  Clone the project to your local repository and run from the IDE.
    
## Instructions

### Using the Endpoints
Once the application is running, go to the following URL which will bring up the Swagger UI interface on your localhost:

[Swagger UI](localhost:8080/swagger-ui/index.html#/)

### Users and Authentication
The application is designed to have Spring create the schema and populate the tables with sample data upon startup. The sample data includes two users -"johnnyb" and "harold".

- User "johnnyb" has the role of ADMIN and his password is "mypass".  "johnnyb" is the owner of project #1.
- User "harold" has the role of USER and his password is password1.  "harold" is the owner of project #2.

#### To log in as any user:
    1. Invoke the "Authenticate a user" endpoint under the User Authentication controller.  You do this by clicking the "Try it out" button under the enpoint.
    2. After editing the example request body with the correct username and password, click on the "Execute" button.
    3. If succesful, the response body will include a JWT access token. After copying this access token to your clipboard, click on the "Authorize" button that appears toward the top of the Swagger UI page and paste the token in the "Value" box.
    4. Next, click on the "Authorize" button.  At this point, you should be logged in.
*Note that each time you restart the application, you will need to re-authenticate and get a new access token.

#### Other Notes on Security
- Once a user is logged out, the user will need to re-authenticate, acquire a new access token, and log in with the new access token.
- An access token is set to expire after 24 hours. This application includes a feature to refresh tokens, but it won't be covered here.

#### Registering new users:
You can register a new user by using the "Register a new user" endpoint. After clicking the "Try it out" button, use the example request body to provide the needed details of the new user.

*Note that this endpoint will always assign the USER role to the new user. To convert the user to an ADMIN, you will need to manually edit the user's role in the user table via a database client like dBeaver of mySQL Workbench. A future release may include an endpoint that will permit an ADMIN to create another ADMIN.

### Managing Projects
- Every project is owned by a user who manages all the assets under that project.
- A user cannot manage nor see another user's project unless the user has the role of ADMIN.
- Use the "Add a new project" endpoint to create a new project.  Once created, you can then add the other assets for that project using the other endpoints available in the Swagger UI.
- Every request requires the requestor to provide the username that owns the relevant project. Unless the requestor is an ADMIN, the request will fail if the requestor does not own the project. When this happens, the resulting message will state that the provided username doesn't exist (even if it does). This is to discourage the requestor from knowing anything about any other users.
- Use the Swagger UI to browse all the available endpoints. Where applicable, each endpoint includes an example request body that can be used when making requests.

### Example Data
The application is set up to have Spring use the included schema.sql and data.sql files to recreate and load the database each time it starts.

##### The following is included:
- Two users -"johnnyb" and "harold".  "johhnyb" has the role of "ADMIN" and "harold" has the role of USER.
- Two projects -one project under "johnnyb" and another under "harold".
- Various assets under each project.

You can work off of the existing data or create new projects from scratch using the various endpoints.


