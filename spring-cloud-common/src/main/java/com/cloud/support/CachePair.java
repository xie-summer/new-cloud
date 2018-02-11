package com.cloud.support;

import java.io.Serializable;
/**
 * @author summer
 */
public class CachePair implements Serializable {
    private static final long serialVersionUID = -2099185753636160455L;
    private Long version;
    private Object value;
    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public CachePair(){}
}
