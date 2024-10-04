FROM 192.168.16.148:10004/paas/jdk-centos-amd64:8u361

WORKDIR /cdss-codegen

ARG JAR_FILE=target/visual-codegen.jar

COPY ${JAR_FILE} app.jar

EXPOSE 5002

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar app.jar
