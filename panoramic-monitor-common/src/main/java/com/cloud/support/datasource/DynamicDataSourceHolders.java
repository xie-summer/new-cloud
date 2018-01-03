package com.cloud.support.datasource;

/**
 * @author summer
 */
public class DynamicDataSourceHolders {

    public static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void putDataSource(String name) {
        holder.set(name);
    }

    public static String getDataSource() {
        return holder.get();
    }
}
