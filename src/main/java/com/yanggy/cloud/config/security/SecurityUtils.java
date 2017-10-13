package com.yanggy.cloud.config.security;

import com.yanggy.cloud.config.jwt.JWTUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by derrick.yang on 9/27/17.
 */
public final class SecurityUtils {

    /**
     * security缓存中保存的用户信息
     * @return JWTUser
     */
    public static JWTUser getSecurityUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(null == user) {
            return null;
        }

        return (JWTUser)user;
    }
}
