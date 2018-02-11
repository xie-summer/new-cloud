package com.monitor.api.acl;


import com.monitor.model.api.ApiUser;

import java.util.List;

/**
 * @author sunmer
 */
public interface ApiService {
    List<ApiUser> getApiUserList(String status);
}

