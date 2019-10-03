package com.example;
import bitronix.tm.TransactionManagerServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.drools.core.marshalling.impl.ClassObjectMarshallingStrategyAcceptor;
import org.drools.core.marshalling.impl.SerializablePlaceholderResolverStrategy;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.marshalling.ObjectMarshallingStrategy;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.process.ProcessInstance;


import org.kie.api.task.TaskService;
import org.kie.api.task.UserGroupCallback;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UIServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.Random;

/**
 * Created by simon on 01/10/19.
 */
public  class ProcessTest extends JbpmJUnitBaseTestCase {
    protected static final String PROCESO_PRINCIPAL = "ProyectoInversion.ProyectoDeInversion_ProcesoPrincipal";
    protected static final String REQUERIMIENTO_P = "requerimiento_p";
    protected static final String REQUERIMIENTO_T = "requerimiento_t";
    protected static final String SOW_P = "sow_p";
    protected static final String SOW_T = "sow_t";
    protected static final String PRESUPUESTO_P = "presupuesto_p";
    protected static final String PRESUPUESTO_T = "presupuesto_t";
    protected static final String ENV = "env";
    protected static final String ENV_T = "env_t";
    protected static final String PROYECTO_T = "proyecto_t", PROYECTO_P = "proyecto_p";

    private static Random random = new Random(System.currentTimeMillis());
    protected UserGroupCallback userGroupCallback = new JBossUserGroupCallbackImpl("classpath:users.properties");

    protected KieSession ksession = null;
    protected AuditService auditService = null;
    protected TaskService taskService = null;

    protected ObjectMapper mapper = new ObjectMapper();

    {
        super.userGroupCallback = userGroupCallback;
        //System.setProperty("org.jbpm.var.log.length", "7999");
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();

        addEnvironmentEntry(EnvironmentName.ENTITY_MANAGER_FACTORY, getEmf());

        addEnvironmentEntry(EnvironmentName.TRANSACTION_MANAGER,
                TransactionManagerServices.getTransactionManager());

        addEnvironmentEntry(EnvironmentName.OBJECT_MARSHALLING_STRATEGIES,
                new ObjectMarshallingStrategy[] {
                        new SerializablePlaceholderResolverStrategy(ClassObjectMarshallingStrategyAcceptor.DEFAULT) });

       /* addWorkItemHandler("Rest",
                new ar.com.fluxit.wih.CustomRestWorkItemHandler(ThreadLocal.class.getClassLoader()));

        addWorkItemHandler("JPA", new ar.com.fluxit.wih.JPAWorkItemHandler("wissen:ProyectoInversion:TEST",
                ThreadLocal.class.getClassLoader()));*/



        // Leo el proceso BPMN del proyecto kie
      //  String principal = "com/sample/sample.bpmn";
       // createRuntimeManager(Strategy.SINGLETON, "", principal);
       // RuntimeEngine runtimeEngine = getRuntimeEngine(EmptyContext.get());

        // Obtengo los servicios
       /* ksession = runtimeEngine.getKieSession();
        auditService = runtimeEngine.getAuditService();
        taskService = runtimeEngine.getTaskService();
*/
      /*  assertNotNull(ksession);
        assertNotNull(auditService);
        assertNotNull(taskService);*/

    }




    @Test
    public void testProcess() {
    //    KieServices ks = KieServices.Factory.get();


      /*  RuntimeManager manager = createRuntimeManager("com/sample/sample.bpmn");
        RuntimeEngine engine = getRuntimeEngine(null);
        KieSession ksession = engine.getKieSession();

        ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello");
        // check whether the process instance has completed successfully
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        assertNodeTriggered(processInstance.getId(), "Hello");

        manager.disposeRuntimeEngine(engine);
        manager.close();*/
    }
}
