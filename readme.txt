 Download jbpm-installer-full-7.16.0.Final

 https://download.jboss.org/jbpm/release

 
1 -) Descompilar jbpm-installer-full-7.16.0.Final

2 -) cd jbpm-installer


2.1 -) beyondcompare entre el  de git hub y el  instalador y copiar los archivos

3 -) ant install.demo.noeclipse

4 -) beyondcompare entre el wildfly de git hub y el wildfly del instalador y copiar los archivos

5 -) Crear bd JBPM_LEARN7 con usurio sa y password Ci10169949.
 
6 -) Correr  saymnjbpm.bat qe esta en wildfly_home/bin

   Tiene estos parametros
   
     #Debe estr en un sola linea
    standalone.bat -c standalone-full.xml -Dorg.kie.server.user=kieserver -Dorg.kie.server.pwd=kieserver -Dorg.kie.server.id=saymon-id-kie-server -Dorg.kie.server.location=http://localhost:8080/kie-server/services/rest/server -Dorg.kie.server.user=kieserver -Dorg.kie.server.controller.user=kieserver -Dorg.kie.server.controller.pwd=kieserver -Dorg.kie.server.persistence.ds=java:jboss/datasources/jbpmDS -Dorg.kie.demo=false -Dorg.kie.server.persistence.dialect=org.hibernate.dialect.SQLServer2008Dialect -b 0.0.0.0

     #Crear usuario

     Con add-user, usar ApplicationRealm  ,  y pasar el role y usuario que se genero en application-users.properties, application-roles.properties a lo archivos users.properties, roles.properties

     user : saymon
     password : saymon

     roles : admin,kie-server,rest-all




/bin/sh $JBOSS_HOME/bin/add-user.sh
  --user-properties $JBOSS_HOME/standalone/configuration/users.properties
  --group-properties $JBOSS_HOME/standalone/configuration/roles.properties
  --realm ApplicationRealm
     
     #.... Probar la instalacion

     http://localhost:8080/kie-server/services/rest/server
         usuario = saymon, password = saymon

http://localhost:8080/kie-server/services/rest/server/queries/processes/definitions

7 -) Ir a la carpeta    porbarEjemplo

y leer el readme.txt para probarlo