package com.ifacebox.web.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author znn
 */
public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    public static <T> T readValue(String content, Class<T> valueType) {
        try (StringReader stringReader = new StringReader(content)) {
            JAXBContext context = JAXBContext.newInstance(valueType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return valueType.cast(unmarshaller.unmarshal(stringReader));
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("解析Xml异常：" + e.toString(), e);
            }
        }
        return null;
    }

    public static String writeValueAsString(Object value) {
        try (StringWriter stringWriter = new StringWriter()) {
            JAXBContext context = JAXBContext.newInstance(value.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(value, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("解析Xml异常：" + e.toString(), e);
            }
        }
        return null;
    }
}
