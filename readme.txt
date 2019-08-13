docker-compose -f jbpm-full-mysql.yml up --build -d
#Start
#Linux: $ ./standalone.sh &
#
#Windows: > standalone.bat
#
#Stop
#Linux: $ ./jboss-cli.sh --connect command=:shutdown
#
#Windows: > jboss-cli.bat --connect command=:shutdown
#
#Restart
#Linux: $ ./jboss-cli.sh --connect command=:reload
#
#Windows: > jboss-cli.bat --connect command=:reload

#shutdown contenedor
docker-compose -f jbpm-full-mysql.yml down

#arrancar contenedor
docker-compose -f jbpm-full-mysql.yml up --build -d

#ver los logs
docker logs -f jbpm_docker_jbpm_1

docker exec -it survey-all_mysql_ecolo_container_1 bash
mysql -uroot -p123456
create database jbpm;

http://localhost:8080/kie-server/services/rest/server

http://localhost:8080/business-central/kie-wb.jsp