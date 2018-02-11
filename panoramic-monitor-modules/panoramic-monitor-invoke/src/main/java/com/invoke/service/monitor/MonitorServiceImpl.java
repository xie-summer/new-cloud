package com.invoke.service.monitor;

import com.invoke.model.monitor.MonitorEntry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author summer
 */
@Service("monitorService")
public class MonitorServiceImpl extends AbstractMonitorService implements InitializingBean {
    @Override
    public void addMonitorEntry(String var1, Map<String, String> var2) {

    }

    @Override
    public void addMonitorEntry(MonitorEntry var1) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
