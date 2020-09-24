@echo off

title %~dp0
cls
color 0A
echo =====使用方法============================================================
echo 执行路径		%~dp0
echo 指定参数运行方法	java -jar -Dserver.port=[端口] -Dserver.servlet.context-path=[/路径] [名字].jar
echo 指定配置文件运行方法	java -jar -Dspring.config.location=[路径/application.yml] [名字].jar
echo =====使用方法============================================================

java -jar -Dspring.config.location=application.yml adx-0.0.1-SNAPSHOT.jar

pause