FROM maven:3.8.1-openjdk-17-slim AS image

# Defining default non-root user UID, GID, and name 
# ARG USER_UID="1000"
# ARG USER_GID="1000"
# ARG USER_NAME="user"

# Creating default non-user 
# RUN groupadd -g $USER_GID $USER_NAME && \
#   useradd -m -g $USER_GID -u $USER_UID $USER_NAME

# Switching to non-root user to install SDKMAN! 
# USER $USER_UID:$USER_GID


FROM image AS builder
WORKDIR /app
COPY social-network social-network
COPY social-network-parent social-network-parent
COPY pom.xml .
RUN mvn clean install -DskipTests


FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# copy jar from the first stage
COPY --from=builder /app/social-network/web/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
