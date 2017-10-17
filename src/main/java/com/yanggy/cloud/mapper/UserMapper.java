package com.yanggy.cloud.mapper;


import com.yanggy.cloud.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangguiyun on 2017/6/1.
 */

@Mapper
public interface UserMapper {
    User findByName(@Param("username") String name);
    List<String>getUserRoles(@Param("userId") Long userId);
}
