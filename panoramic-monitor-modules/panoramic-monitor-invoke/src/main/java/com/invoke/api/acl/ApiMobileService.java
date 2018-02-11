package com.invoke.api.acl;


import com.invoke.model.api.ApiUser;


/**
 * @author summer
 */
public interface ApiMobileService {

    /**
     * @param partnerid
     * @return
     */
    boolean isPartner(Long partnerid);

    /**
     *
     */
    void initApiUserList();

    /**
     * @param appkey
     * @return
     */
    ApiUser getApiUserByAppkey(String appkey);
}
