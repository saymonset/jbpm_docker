 Download jbpm-6.4.0.Final-installer-full

 https://download.jboss.org/jbpm/release/6.4.0.Final/

 
Actualizar eclipse con
    https://download.jboss.org/jbpm/release/6.4.0.Final/updatesite/

1 -) Descompilar jbpm-6.4.0.Final-installer-full

2 -) cd jbpm-installer


2.1 -) beyondcompare entre el  de git hub y el  instalador y copiar los archivos

3 -) ant install.demo.noeclipse

4 -) beyondcompare entre el wildfly de git hub y el wildfly del instalador y copiar los archivos

5 -) Crear bd JBPM_LEARN con usurio sa y password Ci10169949.
 
6 -) Correr  saymnjbpm.bat qe esta en wildfly_home/bin

   Tiene estos parametros
   
     #Debe estr en un sola linea
     standalone.bat -c standalone-full.xml -Dorg.kie.server.user=kieserver -Dorg.kie.server.pwd=kieserver -Dorg.kie.server.id=saymon-id-kie-server -Dorg.kie.server.location=http://localhost:8080/kie-server/services/rest/server -Dorg.kie.server.user=kieserver -Dorg.kie.server.controller.user=kieserver -Dorg.kie.server.controller.pwd=kieserver -Dorg.kie.server.persistence.ds=java:jboss/datasources/wissenDS -Dorg.kie.demo=false -Dorg.kie.server.persistence.dialect=org.hibernate.dialect.SQLServer2008Dialect -b 0.0.0.0

     
     #.... Probar la instalacion

     http://localhost:8080/kie-server/services/rest/server
         usuario = admin, password = admin

http://localhost:8080/kie-server/services/rest/server/queries/processes/definitions

7 -) Ir a la carpeta    porbarEjemplo

y leer el readme.txt para probarlo