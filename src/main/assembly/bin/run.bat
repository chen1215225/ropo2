@echo off
echo "run delai tops"
cd /d %~dp0\..
java java -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod -jar bees-report.jar
pause