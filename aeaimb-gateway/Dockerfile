FROM 192.168.16.148:10004/paas/centos-amd64:deploy
VOLUME /tmp
MAINTAINER huangzhengri
EXPOSE 9999
ENV PARAMS=""
ENV JAVA_OPTS=""
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/aeaimb-gateway.jar /app.jar

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Duser.timezone=GMT+08 -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -jar /app.jar $PARAMS"]
