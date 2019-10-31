1-)   Bajar el zip sencillo ( Download jBPM 7.28.0.Final server (single zip) distribution). Es el mas estable..

      https://www.jbpm.org/download/download.html

       Maciej Swiderski
	I’d say go with server zip distribution as it is being used by jBPM team itself so most likely way more stable and described in the getting started guide.

        

2-) Realizar el beyondcompare para configurar con mysql bd jbpm

3-) Instalar motor mysql con jbpm bd

3-) En caso que el server missin en difinicon , borrar el archivo xml (org.kie.server.id) y correr de nuevo

4-) En caso que falte memoria.. sacar la configuracion de expandir memoria de standalone.conf
 y ponerla en /bin/standalone.conf del wildfly.. en este caso de 7.28.0.Final server

5-) Si quieres configurar con otras base de datos, como por ejemplo postgresql, ir a esta url
https://jbpm.org/learn/gettingStarted.html
