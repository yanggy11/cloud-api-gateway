package com.yanggy.cloud.config.jwt;

import com.yanggy.cloud.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yangguiyun on 2017/9/26.
 */
public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JWTUser create(User user, List<String>roles) {
        return new JWTUser(
                user.getId(),
                user.getName(),
                user.getPassword(),
                null,
                mapToGrantedAuthorities(roles),
               null,
                user.getAvater()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
