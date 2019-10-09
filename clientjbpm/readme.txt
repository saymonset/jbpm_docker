gradle clean build
gradle bootrun
Example http://localhost:8445/q/all
http://localhost:8445/q/newInstance

Levantar swagger
http://localhost:8445/swagger-ui.html#


mvn install:install-file -DgroupId=jbpm_client_nps -DartifactId=jbpm_nps -Dversion=1.0 -Dpackaging=jar -Dfile=/opt/workspace_angular/jbpm/jbpm-DOCKER/github/jbpm_docker/clientjbpm/build/libs/clientjbpm-0.0.1-SNAPSHOT.jar