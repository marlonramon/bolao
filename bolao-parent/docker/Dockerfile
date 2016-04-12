FROM        java:8-jdk

MAINTAINER  <thiago.busarello@datacoper.com.br>

ENV         JAVA_HOME         /usr/lib/jvm/java-8-openjdk-amd64
ENV         GLASSFISH_HOME    /usr/local/glassfish4
ENV         PATH              $PATH:$JAVA_HOME/bin:$GLASSFISH_HOME/bin

#ENV         http_proxy http://usuario:senha@10.150.51.44:3128
#ENV         https_proxy http://usuario:senha@10.150.51.44:3128
RUN         apt-get update && apt-get install -y curl unzip zip && rm -rf /var/lib/apt/lists/* && apt-get clean

RUN         curl -L -o /tmp/glassfish-4.1.zip http://download.java.net/glassfish/4.1/release/glassfish-4.1.zip && \
            unzip /tmp/glassfish-4.1.zip -d /usr/local && \
            rm -f /tmp/glassfish-4.1.zip

WORKDIR     /usr/local/glassfish4

RUN         curl -L -o glassfish/domains/domain1/lib/mysql-connector-java-5.1.38.jar http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar

RUN         mkdir bolao 
COPY        glassfish-resources.xml bolao

RUN         asadmin start-domain domain1 && asadmin add-resources bolao/glassfish-resources.xml 
#asadmin enable-secure-admin

COPY        bolao-web.war /usr/local/glassfish4/glassfish/domains/domain1/autodeploy

CMD ["asadmin", "start-domain", "-v"]

EXPOSE 8080 4848
