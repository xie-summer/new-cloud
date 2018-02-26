FROM java:8
VOLUME /tmp
ADD spring-cloud-config-server.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 4001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]