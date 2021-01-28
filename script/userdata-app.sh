#!/bin/bash

# envs
export BUCKET=fc-monolothic-ec2-logs
export VERSION=v1.0
export REGION="ap-northeast-1"
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL="jdbc:mysql://db.fc-monolithic.in:3306/mobility"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD="password"
export NAVER_KEY=""
export NAVER_SECRET=""
export JAVA_OPTS="-server -Xms512m -Xmx512m -XX:NewRatio=3 -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -verbose:gc -Xlog:gc:gc.log -XX:+DisableExplicitGC -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Dnetworkaddress.cache.ttl=0"

yum update -y

# awslogs configuration & start
yum install -y awslogs

tee -a /etc/awslogs/awscli.conf > /dev/null <<EOF
[plugins]
cwlogs = cwlogs
[default]
region = ${REGION}
EOF

tee -a /etc/awslogs/awslogs.conf > /dev/null <<EOF
[app-log]
file = /var/log/spring/app.log
log_group_name = /var/log/fc-monolithic/app.log
log_stream_name = {instance_id}
datetime_format = %b %d %H:%M:%S
EOF

systemctl start awslogsd

# git & jdk 11
yum install git -y
amazon-linux-extras install java-openjdk11 -y

# s3 download
aws s3 cp s3://${BUCKET}/${VERSION}/app.jar app.jar

# Start Spring Boot
mkdir -p /var/log/spring
java $JAVA_OPTS -jar app.jar

