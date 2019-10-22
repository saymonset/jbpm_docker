package com.jbpm.dbusergroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.kie.internal.task.api.UserGroupCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Created by simon on 12/10/19.
 */
public class DBUserGroupCallbackImpl extends AbstractUserGroupInfo implements UserGroupCallback {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DBUserGroupCallbackImpl.class);

    protected static final String DEFAULT_PROPERTIES_NAME = "classpath:/jbpm.usergroup.callback.properties";

    public static final String DS_JNDI_NAME = "db.ds.jndi.name";
    public static final String PRINCIPAL_QUERY = "select userId from User where userId = ?";
    public static final String USER_ROLES_QUERY = "select groupId from Groups where groupId = ?";
    public static final String ROLES_QUERY = "select groupId from Groups where userId = ?";
    /**
     *
     *         props.setProperty(DBUserGroupCallbackImpl.PRINCIPAL_QUERY, "select userId from User where userId = ?");
     props.setProperty(DBUserGroupCallbackImpl.ROLES_QUERY, "select groupId from Groups where groupId = ?");
     props.setProperty(DBUserGroupCallbackImpl.USER_ROLES_QUERY, "select groupId from Groups where userId = ?");
     * */

    private Properties config;
    private DataSource ds;

    //no no-arg constructor to prevent cdi from auto deploy
    public DBUserGroupCallbackImpl(boolean activate) {
        String propertiesLocation = System.getProperty("jbpm.usergroup.callback.properties");
        config = readProperties(propertiesLocation, DEFAULT_PROPERTIES_NAME);
        init();

    }

    public DBUserGroupCallbackImpl(Properties config) {
        this.config = config;
        init();

    }

    public boolean existsUser(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        return checkExistence(this.config.getProperty(PRINCIPAL_QUERY), userId);
    }

    public boolean existsGroup(String groupId) {
        if (groupId == null) {
            throw new IllegalArgumentException("GroupId cannot be null");
        }
        return checkExistence(this.config.getProperty(ROLES_QUERY), groupId);
    }

    public List<String> getGroupsForUser(String userId) {

        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }

        List<String> roles = new ArrayList<String>();

        List<String> roleStrs = jdbcTemplate.query(USER_ROLES_QUERY,
                new BeanPropertyRowMapper<>(String.class));

        /*Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();

            ps = conn.prepareStatement(this.config.getProperty(USER_ROLES_QUERY));
            try {
                ps.setString(1, userId);
            } catch (ArrayIndexOutOfBoundsException ignore) {

            }
            rs = ps.executeQuery();
            while (rs.next()) {
                roles.add(rs.getString(1));
            }
        } catch (Exception e) {
            logger.error("Error when checking roles in db, parameter: " + userId, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
        }*/

        return roles;
    }

    protected Connection getConnection() throws SQLException {

        return ds.getConnection();
    }

    private void init() {
        if (this.config == null || !this.config.containsKey(DS_JNDI_NAME) ||
                !this.config.containsKey(PRINCIPAL_QUERY) || !this.config.containsKey(ROLES_QUERY)
                || !this.config.containsKey(USER_ROLES_QUERY)) {
            throw new IllegalArgumentException("All properties must be given ("+ DS_JNDI_NAME + ","
                    + PRINCIPAL_QUERY +"," + ROLES_QUERY +"," +USER_ROLES_QUERY +")");
        }
        String jndiName = this.config.getProperty(DS_JNDI_NAME, "java:/DefaultDS");
        try {
            InitialContext ctx = new InitialContext();

            ds = (DataSource) ctx.lookup(jndiName);

        } catch (Exception e) {
            throw new IllegalStateException("Can get data source for DB usergroup callback, JNDI name: " + jndiName, e);
        }
    }

    protected boolean checkExistence(String querySql, String parameter) {
              boolean result=false;

     /*   Map parameters = new HashMap();
        parameters.put("id", id);
        return namedParameterJdbcTemplate.queryForObject(FETCH_SQL_BY_ID, parameters, new UserMapper());


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            conn = ds.getConnection();

            ps = conn.prepareStatement(querySql);

            ps.setString(1, parameter);

            rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (Exception e) {
            logger.error("Error when checking user/group in db, parameter: " + parameter, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
        }*/


        return result;
    }

}


class UserMapper implements RowMapper {

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("name");
    }

}