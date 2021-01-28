#!bin bash

export BUCKET=fc-monolothic-ec2-logs
export VERSION=v1.0

sudo yum update -y

# 처음 빌드때는 clone
git clone https://github.com/SeungpilPark/fastcampus-monolithic
cd fastcampus-monolithic

# 두번째부터는 git pull
# cd fastcampus-monolithic
# git pull

# Web Build
cd frontend
npm install
npm run build

# App build
cd ../
gradle :backend:clean :backend:build

# Zip and Upload
zip -r web.zip web
aws s3 cp web.zip s3://${BUCKET}/${VERSION}/web.zip
aws s3 cp backend/build/libs/backend.jar s3://${BUCKET}/${VERSION}/app.jar






