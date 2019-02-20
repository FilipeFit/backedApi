FROM openjdk:8
COPY  target/backendapi-0.0.1-SNAPSHOT.jar .
EXPOSE 8080:8080
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "backendapi-0.0.1-SNAPSHOT.jar"]