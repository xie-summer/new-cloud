package com.invoke.api.acl;


import com.invoke.model.api.ApiUser;

import java.util.List;

/**
 * @author sunmer
 */
public interface ApiService {
    /**
     * @param status
     * @return
     */
    List<ApiUser> getApiUserList(String status);
}

