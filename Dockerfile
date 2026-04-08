FROM openjdk:17-jdk

# Install required tools for Maven wrapper
RUN apt-get update && apt-get install -y tar gzip && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

# Ensure mvnw has executable permissions
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/prajaconnect-0.0.1-SNAPSHOT.jar"]
