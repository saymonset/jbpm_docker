package org.jbpm.services.task.identity;

import com.jbpm.dbusergroup.AbstractUserGroupInfo;
import com.repository.UserRepository;
import org.kie.api.task.UserGroupCallback;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 21/10/19.
 */
@Service
public class DBUserGroupCallbackImpl extends AbstractUserGroupInfo implements UserGroupCallback {

    @Inject
    private UserRepository userRepository;
    private DataSource ds;
    protected static final String DEFAULT_PROPERTIES_NAME = "classpath:/jbpm.usergroup.callback.properties";

    public static final String DS_JNDI_NAME = "java:jboss/datasources/jbpmDS";
    public static final String PRINCIPAL_QUERY = "select first_name from jbpm.user where login=?";
    public static final String USER_ROLES_QUERY = "select name  from jbpm.user_role r inner join jbpm.user u on u.id=r.user_id where u.login= ?";
    public static final String ROLES_QUERY = "select name  from jbpm.user_role r inner join jbpm.user u on u.id=r.user_id";

    public DBUserGroupCallbackImpl(){

        try {
            InitialContext ctx = new InitialContext();

            ds = (DataSource) ctx.lookup(DS_JNDI_NAME);

        } catch (Exception e) {
            throw new IllegalStateException("Simon Can get data source for DB usergroup callback, JNDI name: " + DS_JNDI_NAME, e);
        }


    }


    public boolean existsUser(String userId) {
        return true;
    }

    @Override
    public boolean existsGroup(String groupId) {
        return true;
    }

    @Override
    public List<String> getGroupsForUser(String userId) {
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        return roles;
    }
}
