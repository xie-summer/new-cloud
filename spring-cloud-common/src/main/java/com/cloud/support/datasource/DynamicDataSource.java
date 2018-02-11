package com.cloud.support.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author summer
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        Object ret = DynamicDataSourceHolders.getDataSource();
        //clear datasource
        DynamicDataSourceHolders.putDataSource(null);
        return ret;
    }

}
