package com.yanggy.cloud.auth;

import com.yanggy.cloud.config.jwt.JWTUser;
import com.yanggy.cloud.config.jwt.JwtTokenUtil;
import com.yanggy.cloud.dto.ResponseEntity;
import com.yanggy.cloud.mapper.UserMapper;
import com.yanggy.cloud.param.UserParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public ResponseEntity<?> login(@RequestBody UserParam userParam) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userParam.getName(), userParam.getPassword());
         Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(upToken);
        }catch (Exception e) {
            e.printStackTrace();
        }

        JWTUser jwtUser = (JWTUser) authentication.getPrincipal();
        final String token = this.tokenHead + jwtTokenUtil.generateToken(jwtUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map map = new HashMap();
        map.put("token",token);
        map.put("user", jwtUser);


        return new ResponseEntity<>(map);
    }

    @PostMapping(value = "validToken")
    public ResponseEntity<?> validToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)) {
            return new ResponseEntity<>("0","","00000001");
        }
        if(StringUtils.isEmpty(jwtTokenUtil.getUsernameFromToken(token))){
            return new ResponseEntity<>("0","","00000001");
        }

        return new ResponseEntity<>();
    }

}
