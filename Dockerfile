FROM maven:3.8.2-openjdk-17
WORKDIR /app
COPY Books /app/.
RUN mvn -f /app/pom.xml package -Dmaven.test.skip=true
CMD mvn spring-boot:run