/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package hu.icellmobilsoft.coffee.tool.utils.marshalling;

import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import jakarta.enterprise.inject.Vetoed;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.coffee.se.logging.Logger;

/**
 * Utility methods for marshalling.
 *
 * @author karcsi
 * @since 1.0.0
 */
@Vetoed
public class MarshallingUtil {

    private static Logger LOGGER = Logger.getLogger(MarshallingUtil.class);

    private static final String WARN_CANNOT_CONVERT = "Cannot convert [{0}] object to xml: [{1}]";

    private static Map<String, JAXBContext> jaxbContextCache = new ConcurrentHashMap<>();

    /**
     * Marshals an object to a {@code String} xml.
     *
     * @param object
     *            source object
     * @return marshalled xml {@code String} or null if marshalling error
     */
    public static String marshall(Object object) {
        try {
            return marshallUncheckedXml(object);
        } catch (JAXBException e) {
            LOGGER.warn(WARN_CANNOT_CONVERT, object, e.getMessage());
            LOGGER.trace("Exception: ", e);
        }
        return null;
    }

    /**
     * Marshals an object to a {@code String} xml.
     *
     * @param object
     *            source object
     * @return marshalled xml {@code String} or null if invalid input
     * @throws JAXBException
     *             on marshalling exception
     */
    public static String marshallUncheckedXml(Object object) throws JAXBException {
        if (object == null) {
            LOGGER.warn("The object is null.");
            return null;
        }
        return marshallUncheckedXml(object, object.getClass());
    }

    private static <T> JAXBContext getJaxbContext(final Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext;
        String className = clazz.getName();
        if (jaxbContextCache.containsKey(className)) {
            jaxbContext = jaxbContextCache.get(className);
        } else {
            jaxbContext = JAXBContext.newInstance(clazz);
            jaxbContextCache.put(className, jaxbContext);
        }
        return jaxbContext;
    }

    /**
     * Marshals an object to a {@code String} xml.
     *
     * @param <T>
     *            object type
     * @param object
     *            source object
     * @param c
     *            object class
     * @return marshalled xml {@code String} or null if invalid input
     * @throws JAXBException
     *             if marshalling error
     */
    public static <T> String marshallUncheckedXml(Object object, Class<T> c) throws JAXBException {
        if (object == null || c == null) {
            LOGGER.warn("The object or type is null.");
            return null;
        }
        JAXBContext jc = getJaxbContext(c);
        Marshaller m = jc.createMarshaller();
        return marshallUncheckedXml(object, m);
    }

    /**
     * Marshals an object to a {@code String} xml.
     *
     * @param object
     *            source object
     * @param m
     *            marshaller
     * @return marshalled xml {@code String} or null if invalid input
     * @throws JAXBException
     *             if marshalling error
     */
    public static String marshallUncheckedXml(Object object, Marshaller m) throws JAXBException {
        if (object == null) {
            LOGGER.warn("The object is null.");
            return null;
        }
        LOGGER.debug("Marshalling object to xml.");
        // docs.oracle: Closing a StringWriter has no effect.
        StringWriter writer = new StringWriter();
        m.marshal(object, writer);
        return writer.toString();
    }

    /**
     * Marshals an object to a stream.
     *
     * @param object
     *            source object
     * @param s
     *            output stream
     */
    public static void marshall(Object object, OutputStream s) {
        try {
            marshallUncheckedXml(object, s);
        } catch (JAXBException e) {
            LOGGER.warn(WARN_CANNOT_CONVERT, object, e.getMessage());
        }
    }

    /**
     * Marshals an object to a stream.
     *
     * @param <T>
     *            object type
     * @param object
     *            source object
     * @param s
     *            output stream
     * @param c
     *            object class
     */
    public static <T> void marshall(Object object, OutputStream s, Class<T> c) {
        try {
            marshallUncheckedXml(object, s, c);
        } catch (JAXBException e) {
            LOGGER.warn(WARN_CANNOT_CONVERT, object, e.getMessage());
        }
    }

    /**
     * Marshals an object to a stream.
     *
     * @param object
     *            source object
     * @param s
     *            output stream
     * @throws JAXBException
     *             if marshalling error
     */
    public static void marshallUncheckedXml(Object object, OutputStream s) throws JAXBException {
        if (object == null || s == null) {
            LOGGER.warn("The object or stream is null.");
            return;
        }
        marshallUncheckedXml(object, s, object.getClass());
    }

    /**
     * Marshals an object to a stream.
     *
     * @param <T>
     *            object type
     * @param object
     *            source object
     * @param s
     *            output stream
     * @param c
     *            object class
     * @throws JAXBException
     *             if marshalling error
     */
    public static <T> void marshallUncheckedXml(Object object, OutputStream s, Class<T> c) throws JAXBException {
        if (object == null || s == null || c == null) {
            LOGGER.warn("The object, type or stream is null.");
            return;
        }
        JAXBContext jc = JAXBContext.newInstance(c);
        Marshaller m = jc.createMarshaller();
        m.marshal(object, s);
    }

    /**
     * Unmarshalls an object from a {@code String}.
     *
     * @param <T>
     *            object type
     * @param str
     *            {@code String} source
     * @param c
     *            object class
     * @return unmarshalled dto object or null if unmarshalling error
     */
    public static <T> T unmarshall(String str, Class<T> c) {
        try {
            return unmarshallUncheckedXml(str, c);
        } catch (Exception e) {
            LOGGER.error("Error during unmarshalling JSON string: " + str, e);
        }
        return null;
    }

    /**
     * Unmarshalls an object from an xml {@code String}.
     *
     * @param <T>
     *            object type
     * @param str
     *            {@code String} source
     * @param c
     *            object class
     * @return unmarshalled dto object or null if invalid input or unmarshalling error
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshallUncheckedXml(String str, Class<T> c) {
        if (StringUtils.isBlank(str) || c == null) {
            LOGGER.warn("The string source or the return type is null.");
            return null;
        }
        try {
            JAXBContext jc = JAXBContext.newInstance(c);
            Unmarshaller um = jc.createUnmarshaller();
            StringReader reader = new StringReader(str);
            return (T) um.unmarshal(reader);
        } catch (JAXBException e) {
            LOGGER.error("Error during unmarshalling XML string: " + str, e);
        }
        return null;
    }

    /**
     * Set the value of the database field if it is necessary.
     *
     * @param <T>
     *            dto object type
     * @param lhs
     *            left hand side of the assign (the dto object)
     * @param rhs
     *            right hand side of the assign (the input value)
     * @param setter
     *            the setter method of the right hand side field (the dto setter)
     */
    public static <T> void fillOptionalField(T lhs, JAXBElement<T> rhs, Consumer<T> setter) {
        if (rhs == null) {
            return;
        }
        if (rhs.isNil() && lhs != null) {
            setter.accept(null);
        } else if (!Objects.equals(lhs, rhs.getValue())) {
            setter.accept(rhs.getValue());
        }
    }

}
