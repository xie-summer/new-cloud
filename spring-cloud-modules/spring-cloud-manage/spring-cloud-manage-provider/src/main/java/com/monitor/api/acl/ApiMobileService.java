package com.monitor.api.acl;


import com.monitor.model.api.ApiUser;

/**
 * @author summer
 */
public interface ApiMobileService {

    boolean isPartner(Long partnerid);

    void initApiUserList();

    ApiUser getApiUserByAppkey(String appkey);
}
