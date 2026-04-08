FROM ubuntu:22.04

# Install Java 17 and required tools
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk tar gzip && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

# Ensure mvnw has executable permissions
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/prajaconnect-0.0.1-SNAPSHOT.jar"]
