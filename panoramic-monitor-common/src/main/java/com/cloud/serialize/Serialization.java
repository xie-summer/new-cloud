package com.cloud.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author summer
 */
public interface Serialization {

    /**
     * get content type id
     *
     * @return content type id
     */
    byte getContentTypeId();

    /**
     * get content type
     *
     * @return content type
     */
    String getContentType();

    /**
     * create serializer
     *
     * @param
     * @param output
     * @return serializer
     * @throws IOException
     */
    ObjectOutput serialize(OutputStream output) throws IOException;

    /**
     * create deserializer
     *
     * @param
     * @param input
     * @return deserializer
     * @throws IOException
     */
    ObjectInput deserialize(InputStream input) throws IOException;

}