package com.invoke.service.monitor;

import com.invoke.model.monitor.MonitorEntry;
import com.invoke.web.util.Config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author summer
 */
public class MapMonitorEntry implements MonitorEntry {
    public static final String HEADER_KEY = "__header__";
    private Map<String, String> rowdata;
    private Map<String, String> headMap;
    private String datatype;
    private byte[] rowid;

    @Override
    public byte[] getRowid() {
        return this.rowid;
    }

    public MapMonitorEntry(String datatype, Map<String, String> rowdata) {
        this.datatype = datatype;
        this.rowdata = new LinkedHashMap(rowdata);
        this.rowdata.put("datatype", datatype);
        this.rowdata.put("systemid", Config.SYSTEMID);
        this.rowdata.put("fromhost", Config.getHostname());
    }

    public MapMonitorEntry(String datatype, byte[] rowid, Map<String, String> rowdata) {
        this(datatype, rowdata);
        this.rowid = rowid;
    }

    @Override
    public String getDatatype() {
        return this.datatype;
    }

    @Override
    public Map<String, String> getDataMap() {
        return this.rowdata;
    }

    @Override
    public Map<String, String> getHeadMap() {
        return this.headMap;
    }

    public void setHeadMap(Map<String, String> headMap) {
        this.headMap = headMap;
    }
}
