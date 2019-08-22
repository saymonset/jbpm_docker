    ##
#Un ejemplo para empezar
http://www.mastertheboss.com/jboss-jbpm/jbpm6/running-rules-on-wildfly-with-kie-server

1)  Bajar el kjar
     https://github.com/jesuino/hello-kie-server

      Compilarlo
          mvn clean package install

1.1) Instalar postman y ver imgenes..          
     En  la imagen 3, En la etiqueta BODY, seleccionar option binary y 
     Elegir archivo createHelloContainer.xml por dar un ejemplo


2)  Usar comillas dobles      


  curl -X PUT -H "Content-type: application/xml" -u admin:admin --data @createHelloContainer.xml http://localhost:8080/kie-server/services/rest/server/containers/hello

  3-) Ver los containers
     http://localhost:8080/kie-server/services/rest/server/containers/

  4-) Ver  solo un containers
  
  http://localhost:8080/kie-server/services/rest/server/containers/saymontestcontainer   


  5-) Probamos las reglas del negocio

  curl -X POST -H "X-KIE-ContentType: JSON" -H "Content-type: application/json" -u admin:admin --data @droolsCommands.json http://localhost:8080/kie-server/services/rest/server/containers/instances/hello


 6-) Let's test Business Processes



curl -X POST -H "Content-type: application/json" -H "X-KIE-ContentType: JSON" -u admin:admin --data @startProcess.json http://localhost:8080/kie-server/services/rest/server/containers/hello/processes/hello/instances
 
   curl -X POST -H "Content-type: application/json" -H "X-KIE-ContentType: JSON" -u "admin:admin" --data @startProcess.json http://localhost:8080/kie-server/services/rest/server/containers/saymontestcontainer/processes/hello/instances

  curl -X POST -H "X-KIE-ContentType: JSON" -H "Content-type: application/json" -u "admin:admin" --data @droolsCommands.json http://localhost:8080/kie-server/services/rest/server/containers/instances/saymontestcontainer

