package com.config;
import org.kie.server.api.commands.CommandScript;
import org.kie.server.api.commands.DescriptorCommand;
import org.kie.server.api.model.ItemList;
import org.kie.server.api.model.KieServerCommand;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.Wrapped;
import org.kie.server.api.model.definition.QueryFilterSpec;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.impl.QueryServicesClientImpl;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kie.server.api.rest.RestURI.*;
/**
 * Created by simon on 14/08/19.
 */
public class WissenQueryServicesClientImpl {
    /*extends
 QueryServicesClientImpl{
    @Value("${baseURI}")
    private String baseURI;
    public WissenQueryServicesClientImpl(KieServicesConfiguration config) {
        super(config);
    }

    public WissenQueryServicesClientImpl(KieServicesConfiguration config, ClassLoader classLoader) {
        super(config, classLoader);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T> List<T> query(String queryName, String mapper, QueryFilterSpec filterSpec, Integer page, Integer pageSize, Class<T> resultType) {
        Object result = null;
        Class<?> resultTypeList = getResultTypeList(resultType);
        if( config.isRest() ) {
            Map<String, Object> valuesMap = new HashMap<String, Object>();
            valuesMap.put(QUERY_NAME, queryName);

            String queryString = getPagingQueryString("?mapper="+mapper, page, pageSize);

            result = makeHttpPostRequestAndCreateCustomResponse(
                    build(baseURI, QUERY_DEF_URI + "/" + RUN_FILTERED_QUERY_DEF_POST_URI, valuesMap) + queryString, filterSpec, resultTypeList);


        } else {
            CommandScript script = new CommandScript( Collections.singletonList( (KieServerCommand)
                    new DescriptorCommand( "QueryDataService", "queryFiltered", serialize(filterSpec), marshaller.getFormat().getType(), new Object[]{queryName, mapper, page, pageSize}) ) );
            ServiceResponse<Object> response = (ServiceResponse<Object>) executeJmsCommand( script, DescriptorCommand.class.getName(), "BPM" ).getResponses().get(0);

            throwExceptionOnFailure(response);

            result = response.getResult();
        }


        if (result != null) {
            if (result instanceof ItemList) {
                return ((ItemList<T>) result).getItems();
            } else if (result instanceof List) {
                return (List) result;
            } else if (result instanceof Wrapped) {
                return (List)((Wrapped) result).unwrap();
            }
        }

        return Collections.emptyList();
    }*/
}
