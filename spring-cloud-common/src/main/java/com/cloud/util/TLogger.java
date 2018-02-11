package com.cloud.util;

import java.util.Map;

/**
 * @author summer
 */
public interface TLogger {
    /**
     * @param msg
     */
    void warn(String msg);

    /**
     * @param msg
     */
    void error(String msg);

    /**
     * @param msg
     * @param e
     */
    void warn(String msg, Throwable e);

    /**
     * @param e
     * @param rows
     */
    void warn(Throwable e, int rows);

    /**
     * @param msg
     * @param e
     * @param rows
     */
    void warn(String msg, Throwable e, int rows);

    /**
     * @param msg
     * @param e
     */
    void error(String msg, Throwable e);

    /**
     * @param msg
     * @param e
     * @param rows
     */
    void error(String msg, Throwable e, int rows);

    /**
     * @param e
     * @param rows
     */
    void error(Throwable e, int rows);

    /**
     * @param msgMap
     */
    void warnMap(Map msgMap);

    /**
     * @param type
     * @param msgMap
     */
    void warnMap(String type, Map msgMap);

    /**
     * @param msgMap
     */
    void errorMap(Map msgMap);

    /**
     * @param type
     * @param msgMap
     */
    void errorMap(String type, Map msgMap);

    /**
     * @param type
     * @param msg
     * @param e
     */
    void warnWithType(String type, String msg, Throwable e);

    /**
     * @param type
     * @param msg
     * @param e
     * @param rows
     */
    void warnWithType(String type, String msg, Throwable e, int rows);

    /**
     * @param type
     * @param msg
     */
    void warnWithType(String type, String msg);

    /**
     * @param type
     * @param msg
     */
    void errorWithType(String type, String msg);

    /**
     * @param type
     * @param msg
     * @param e
     */
    void errorWithType(String type, String msg, Throwable e);

    /**
     * @param type
     * @param msg
     * @param e
     * @param rows
     */
    void errorWithType(String type, String msg, Throwable e, int rows);
}
