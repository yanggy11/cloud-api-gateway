package com.yanggy.cloud.route;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author: yangguiyun
 * @Date: 2017/10/12 15:27
 * @Description:
 *  自定义动态路由实体类
 */

@Data
public class Route implements Serializable {
    /**
     * 因为ZuulRoute中id表示路由的id,因此这里使用dynamicId来区分。
     */
    private Long dynamicId;
    private String id;
    private String path;
    private String serviceId;
    private String url;
    private boolean stripPrefix = true;
    private Boolean retryable;
    private Boolean enabled;
}
