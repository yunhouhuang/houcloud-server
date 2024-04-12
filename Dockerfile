# Dockerfile镜像构建文件
FROM openjdk

MAINTAINER yunhouhuang@gmail.com

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /houcloud-server

WORKDIR /houcloud-server

EXPOSE 1027

ADD ./target/houcloud-server.jar ./

CMD sleep 1;java -Xms128m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar houcloud-server.jar
