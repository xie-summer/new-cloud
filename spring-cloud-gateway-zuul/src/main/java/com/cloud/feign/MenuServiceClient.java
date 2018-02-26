package com.cloud.feign;

import com.cloud.common.vo.MenuVo;
import com.cloud.feign.fallback.MenuServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author summer
 * @date 2017/10/31
 */
@FeignClient(name = "monitor-manage", fallback = MenuServiceFallbackImpl.class)
@Service
public interface MenuServiceClient extends MenuService {
    /**
     * 通过角色名查询菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    @GetMapping(value = "/menu/findMenuByRole/{role}")
    Set<MenuVo> findMenuByRole(@PathVariable("role") String role);
}
