# Start with a base image containing Java runtime
FROM appinair/jdk11-maven

# Add Author info
LABEL maintainer="055055"

# Add a volume to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
#EXPOSE 8081

# The application's jar file
ARG JAR_FILE=/user-api/build/libs/user-api.jar

# Add the application's jar to the container
ADD ${JAR_FILE} user-api.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/user-api.jar"]

