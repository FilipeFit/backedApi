The BackEnd API is a application build with Java 8 with Spring framework. This application is designed to expose the
endpoint that is described in the swagger.yaml in the application source

To run this application from a Spring boot perspective just tun the class BackendapiApplication.java.

To build a container with the application it's necessary to build the container using the Dockerfile that is
already in the application source by the command "docker build -t backendApi . " after this comand finish just
run the container with the command "docker run  -p 8080:8080 backendApi:latest"by running those commands a
container with Java 8 and the application will run and expose the port 8080 to reach the endpoint described by the swagger.