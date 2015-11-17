FROM java:8u40

ADD maven/fluent-registry-service-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

CMD ["java", "-Dregistry.jdbc.url=jdbc:mysql://registry-db:3306/registry?relaxAutoCommit=true&autoReconnect=true&failOverReadOnly=false&maxReconnects=10", "-Dregistry.jdbc.username=root", "-Dregistry.jdbc.password=registry", "-jar", "/app.jar"]