FROM java:8u40

ADD maven/fluent-registry-service-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar

CMD ["java", "-Dregistry.jdbc.url=jdbc:mysql://registry-db:33606/registry?relaxAutoCommit=true", "-Dregistry.jdbc.username=root", "-Dregistry.jdbc.password=registry", "-jar", "/app.jar"]