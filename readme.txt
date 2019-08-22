#Bajar la aplicacion
docker-compose -f jbpm-full-mysql.yml down
#Arrancar la aplicacion
docker-compose -f jbpm-full-mysql.yml up --build -d


#Entrar en wildfly internamente
docker exec -it jbpm_docker_jbpm_1 bash

#levantar y shutdown wildfly
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

#Search a word into folder
#grep -R "saymon1_1.0.0-SNAPSHOT" wildfly/
#Fix Error Caused by: org.eclipse.jgit.errors.MissingObjectException: Missing unknown afd55ee0e6e95dbbc7178e60b4f95221de5b1caf
#Borrar lo que esta dentro de repo/jboss y repo/wb_git


#remove all volume
#docker volume ls -qf dangling=true | xargs -r docker volume rm

#Borra las imagenes none
#docker images | grep none | awk '{ print $3; }' | xargs docker rmi
#Borra todos los volumenes
docker volume prune

#Search a word into folder
grep -R "saymon1_1.0.0-SNAPSHOT" wildfly/
grep -R "com.myspace" .

BUGS FIX
Starting jbpm_docker_jbpm_1 ... error

ERROR: for jbpm_docker_jbpm_1  Cannot start service jbpm: network 3a5289f1d8b2db67a358849850fee69ef420aced85eb4231927bfed71298a39e not found

ERROR: for jbpm  Cannot start service jbpm: network 3a5289f1d8b2db67a358849850fee69ef420aced85eb4231927bfed71298a39e not found
ERROR: Encountered errors while bringing up the project.

docker-compose -f jbpm-full-mysql.yml down
docker-compose -f jbpm-full-mysql.yml up --build -d