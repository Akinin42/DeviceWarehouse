FROM tomcat:8.5-jdk15-openjdk-oracle
ARG TOMCAT_FILE_PATH=/docker
ENV APP_DATA_FOLDER=/var/lib/SampleApp
ENV SAMPLE_APP_CONFIG=${APP_DATA_FOLDER}/config/
WORKDIR /usr/local/tomcat/webapps/
COPY /target/warehouse.war /usr/local/tomcat/webapps/warehouse.war
COPY ${TOMCAT_FILE_PATH}/* ${CATALINA_HOME}/conf/
WORKDIR $APP_DATA_FOLDER
EXPOSE 8080
ENTRYPOINT ["catalina.sh", "run"]