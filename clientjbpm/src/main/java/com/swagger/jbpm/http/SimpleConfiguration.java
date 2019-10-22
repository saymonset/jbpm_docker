package com.swagger.jbpm.http;

import com.dto.MamaDTO;
import com.ecological.NpsRresultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.Tools;
import org.apache.http.util.EntityUtils;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.*;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.server.api.model.definition.QueryDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
/**
 * Created by simon on 15/10/19.
 */
public class SimpleConfiguration {
    public void confSimple(){
        // first configure environment that will be used by RuntimeManager
        RuntimeEnvironment environment = RuntimeEnvironmentBuilder.Factory.get()
                .newDefaultInMemoryBuilder()
                .addAsset(ResourceFactory.newClassPathResource("classpath:com/testbpmn.bpmn2"), ResourceType.BPMN2)
                .get();
        // next create RuntimeManager - in this case singleton strategy is chosen
        RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(environment);

        // then get RuntimeEngine out of manager - using empty context as singleton does not keep track
        // of runtime engine as there is only one
        RuntimeEngine runtimeEngine = manager.getRuntimeEngine(EmptyContext.get());

        // get KieSession from runtime runtimeEngine - already initialized with all handlers, listeners, etc that were configured
        // on the environment
        KieSession ksession = runtimeEngine.getKieSession();

        // add invocations to the jBPM engine here,
        // e.g. ksession.startProcess(processId);

        // and last dispose the runtime engine
        manager.disposeRuntimeEngine(runtimeEngine);
    }
}
