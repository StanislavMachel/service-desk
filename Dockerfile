FROM centos

RUN yum install -y java-11-openjdk

VOLUME /tmp

COPY ./web/build/libs/web-0.0.1-SNAPSHOT.jar service-desk.web.jar

RUN sh -c 'touch /service-desk.web.jar'

ENTRYPOINT ["java","-jar","/service-desk.web.jar"]

