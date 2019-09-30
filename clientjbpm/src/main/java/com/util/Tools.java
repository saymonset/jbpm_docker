package com.util;

import com.swagger.jbpm.http.ProcessImages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by simon on 30/09/19.
 */
@Component
public class Tools {
    private static final Logger Log = LoggerFactory.getLogger(ProcessImages.class);
    public Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.info("XMLParser", "Error: "+e.getMessage());
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            Log.info("XMLParser", "Error: "+e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.info("XMLParser", "Error: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
        return doc;
    }
}
