FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD logback.xml /logback.xml
ADD entrypoint.sh /entrypoint.sh
#ADD gitlog.html gitlog.html
ADD api-0.0.1-SNAPSHOT.jar app.jar
RUN chmod 750 /entrypoint.sh
ENTRYPOINT /entrypoint.sh
