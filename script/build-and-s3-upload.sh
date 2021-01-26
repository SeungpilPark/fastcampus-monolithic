#!bin bash

cd ../frontend
npm install
npm run build

cd ../
gradle :backend:clean :backend:build

zip -r web.zip web



