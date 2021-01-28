#!/bin/bash

# envs
export BUCKET=fc-monolothic-ec2-logs
export VERSION=v1.0
export REGION="ap-northeast-1"
export RESOLVER_IP="10.10.0.2"
export WAS_HOST="app.fc-monolithic.in"

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
[web-access-log]
file = /var/log/nginx/access.log
log_group_name = /var/log/fc-monolithic/access.log
log_stream_name = {instance_id}
datetime_format = %b %d %H:%M:%S

[web-error-log]
file = /var/log/nginx/error.log
log_group_name = /var/log/fc-monolithic/error.log
log_stream_name = {instance_id}
datetime_format = %b %d %H:%M:%S
EOF

systemctl start awslogsd

# node
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.34.0/install.sh | bash
. ~/.nvm/nvm.sh
nvm install node
node -e "console.log('Running Node.js ' + process.version)"

# git
yum install git -y

# nginx
amazon-linux-extras install nginx1 -y

# s3 download & unzip
aws s3 cp s3://${BUCKET}/${VERSION}/web.zip web.zip
unzip web.zip

# Prepare contents & nginx conf
cd web
rm -rf /etc/nginx/nginx.conf
mkdir -p /var/www/html
cp -R dist/* /var/www/html/
cp healthchk.html /var/www/html/healthchk.html
cp nginx-default.conf.template /etc/nginx/conf.d/default.conf.template
cp nginx.conf /etc/nginx/nginx.conf -f
envsubst '${RESOLVER_IP} ${WAS_HOST}' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf

# Start Nginx
service nginx start

