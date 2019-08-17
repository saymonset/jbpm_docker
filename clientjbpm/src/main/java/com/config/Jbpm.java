package com.config;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.CaseServicesClient;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.DocumentServicesClient;
import org.kie.server.client.JobServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.SolverServicesClient;
import org.kie.server.client.UIServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ReleaseId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Created by simon on 13/08/19.
 */
@Configuration
public class Jbpm {
    // REST API base URL, credentials, and marshalling format
    private static final String URL = "http://localhost:8180/kie-server/services/rest/server";
    private static final String USER = "salaboy";
    private static final String PASSWORD = "salaboy";


    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private static KieServicesConfiguration conf;

    // KIE client for common operations
    private   KieServicesClient kieServicesClient;

    // Rules client
    private   RuleServicesClient ruleClient;

    // Process automation clients
    private   CaseServicesClient caseClient;
    private   DocumentServicesClient documentClient;
    private   JobServicesClient jobClient;
    private   ProcessServicesClient processClient;
    private   QueryServicesClient queryClient;
    private   UIServicesClient uiClient;
    private   UserTaskServicesClient userTaskClient;

    // DMN client
    private static DMNServicesClient dmnClient;

    // Planning client
    private static SolverServicesClient solverClient;
    {
        initializeKieServerClient();
        initializeDroolsServiceClients();
        initializeJbpmServiceClients();
        initializeSolverServiceClients();
    }

    public   void main(String[] args) {

       // disposeAndCreateContainer();
    }




    public   void initializeKieServerClient() {
        conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        conf.setMarshallingFormat(MarshallingFormat.JAXB);
        kieServicesClient = KieServicesFactory.newKieServicesClient(conf);

    }

    public   void initializeDroolsServiceClients() {
        ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
        dmnClient = kieServicesClient.getServicesClient(DMNServicesClient.class);
    }

    public   void initializeJbpmServiceClients() {
        caseClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
        documentClient = kieServicesClient.getServicesClient(DocumentServicesClient.class);
        jobClient = kieServicesClient.getServicesClient(JobServicesClient.class);
        processClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        queryClient = kieServicesClient.getServicesClient(QueryServicesClient.class);
        uiClient = kieServicesClient.getServicesClient(UIServicesClient.class);
        userTaskClient = kieServicesClient.getServicesClient(UserTaskServicesClient.class);
    }

    public   void initializeSolverServiceClients() {
        solverClient = kieServicesClient.getServicesClient(SolverServicesClient.class);
    }

    @Bean(name = "processServicesClient")
    public ProcessServicesClient processServicesClient() {
        return kieServicesClient.getServicesClient(ProcessServicesClient.class);
    }

    @Bean(name = "queryServicesClient")
    public QueryServicesClient queryServicesClient() {
        return queryClient;
    }

    @Bean(name = "userTaskServicesClient")
    public UserTaskServicesClient userTaskServicesClient() {
        return kieServicesClient.getServicesClient(UserTaskServicesClient.class);
    }

    @Bean(name = "uiServicesClient")
    public UIServicesClient uiServicesClient() {
        return kieServicesClient.getServicesClient(UIServicesClient.class);
    }

    @Bean(name = "kieServicesClient")
    public KieServicesClient kieServicesClient() {

        return kieServicesClient;
    }



}
