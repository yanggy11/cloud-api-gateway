package com.yanggy.cloud.auth;

import com.yanggy.cloud.config.jwt.JWTUser;
import com.yanggy.cloud.config.jwt.JwtTokenUtil;
import com.yanggy.cloud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: yangguiyun
 * @Date: 2017/10/13 13:53
 * @Description:
 */

@RestController
@RequestMapping("/auth/**")
public class AuthController {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserMapper userMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @PostMapping(value="login")
    public Object login(@RequestBody Map<String,String> map) {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(map.get("username"), map.get("password"));
        final Authentication authentication = authenticationManager.authenticate(upToken);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(map.get("username"));
        JWTUser jwtUser = (JWTUser) userDetails;
        final String token = this.tokenHead + jwtTokenUtil.generateToken(jwtUser);

        return token;
    }

}
