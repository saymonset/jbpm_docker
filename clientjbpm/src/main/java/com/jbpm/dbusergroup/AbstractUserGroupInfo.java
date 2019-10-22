package com.jbpm.dbusergroup;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by simon on 12/10/19.
 */
public abstract class AbstractUserGroupInfo {private static final Logger logger = LoggerFactory.getLogger(AbstractUserGroupInfo.class);

    protected Properties readProperties(String propertiesLocation, String defaultProperties) {
        Properties config = null;
        URL locationUrl = null;
        if (propertiesLocation == null) {
            propertiesLocation = defaultProperties;
        }

        logger.debug("Callback properties will be loaded from {}", propertiesLocation);
        if (propertiesLocation.startsWith("classpath:")) {
            String stripedLocation = propertiesLocation.replaceFirst("classpath:", "");
            locationUrl = this.getClass().getResource(stripedLocation);
            if (locationUrl == null) {
                locationUrl = Thread.currentThread().getContextClassLoader().getResource(stripedLocation);
            }
        } else {
            try {
                locationUrl = new URL(propertiesLocation);
            } catch (MalformedURLException e) {
                locationUrl = this.getClass().getResource(propertiesLocation);
                if (locationUrl == null) {
                    locationUrl = Thread.currentThread().getContextClassLoader().getResource(propertiesLocation);
                }
            }
        }
        if (locationUrl != null) {
            config = new Properties();
            try (InputStream stream = locationUrl.openStream()) {
                config.load(stream);
            } catch (IOException e) {
                logger.error("Error when loading properties for DB user group callback", e);
                config = null;
            }
        }

        return config;
    }

}
