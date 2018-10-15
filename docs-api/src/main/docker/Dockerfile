FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD docs-api-0.0.1-SNAPSHOT.jar app.jar
ADD logback.xml /logback.xml
ADD entrypoint.sh /entrypoint.sh
ADD gitlog.html gitlog.html
RUN chmod 750 /entrypoint.sh
ENTRYPOINT /entrypoint.sh
