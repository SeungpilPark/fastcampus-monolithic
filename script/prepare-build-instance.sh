#!bin bash

sudo yum update

# aws cli
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install

# node
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.34.0/install.sh | bash
. ~/.nvm/nvm.sh
nvm install node
node -e "console.log('Running Node.js ' + process.version)"

# git & jdk 11
sudo yum install git -y
sudo amazon-linux-extras install java-openjdk11 -y


# gradle setting
wget https://services.gradle.org/distributions/gradle-6.8.1-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-6.8.1-bin.zip

echo 'export PATH=$PATH:/opt/gradle/gradle-6.8.1/bin' >> ~/.bash_profile
source ~/.bash_profile





