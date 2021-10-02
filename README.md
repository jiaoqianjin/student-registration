# 新生电子注册报到系统升级

## 一、项目背景

​		三月软件技术支持的新生电子注册报到系统为河南科技学院的新生报到保驾护航，提供便利。该系统技术栈主要由SpringBoot + Mybatis构成。近日在学习SpringCloud时，考虑该系统需求明确，又存在高并发的情况。因此利用所学之后，对该系统进行重构升级，替换为SpringCloud为核心的技术栈。

## 二、技术栈

### 技术选型

| 技术栈            | 功能                     |
| ----------------- | ------------------------ |
| Nacos             | 注册中心，配置中心       |
| OpenFeign         | 远程调用                 |
| OAuth2 + JWT      | 权限认证                 |
| Gateway           | 网关，请求分发，统一拦截 |
| Sentinel          | 服务限流，熔断监控       |
| Seata             | 分布式事务               |
| Zipkin + Sleuth   | 链路追踪                 |
| Spring Boot Admin | 状态监控                 |

### 核心依赖

| 依赖                   | 版本          |
| ---------------------- | ------------- |
| Spring Boot            | 2.2.2         |
| Spring Cloud           | Hoxton.SR1    |
| Spring Cloud Alibaba   | 2.1.0.RELEASE |
| Spring Security OAuth2 | 2.1.2.RELEASE |
| Mybatis Plus           | 3.4.1         |
| hutool                 | 5.2.5         |

## 三、模块说明

```
student-registration
├── student-admin -- 业务：管理员模块 [8005]
└── student-common -- 系统公共模块
├── student-data -- 业务：大屏模块 [9001]
├── student-gateway -- Spring Cloud Gateway网关 [9527]
└── student-monitor -- Spring Boot Admin 服务状态监控 [8769]
└── student-oauth -- 通用用户权限管理模块 [9999]
├── student-openid -- 业务：微信模块 [8003]
└── student-praise -- 业务：喜报模块 [8004]
├── student-user -- 业务：用户模块 [8001]
└── student-user-balance -- 业务：用户模块-负载均衡 [8002]
```

## 四、系统架构

![image-20210929204829936](https://gitee.com/jiao_qianjin/zhishiku/raw/master/img/20210929204830.png)

考虑到本地机器的压力，多数服务未做集群，在生产环境下为了应对并发的压力，应该做集群处理。

## 五、服务展示

### 5.1 本地启动服务列表

![image-20210929170056068](https://gitee.com/jiao_qianjin/zhishiku/raw/master/img/20210929170056.png)

### 5.2 Nacos配置中心

![image-20210929165631290](https://gitee.com/jiao_qianjin/zhishiku/raw/master/img/20210929165641.png)

### 5.3 Nacos 配置中心

![image-20210929165931754](https://gitee.com/jiao_qianjin/zhishiku/raw/master/img/20210929165932.png)

### 5.4 SpringBoot Admin 服务监控

![image-20210929165841931](https://gitee.com/jiao_qianjin/zhishiku/raw/master/img/20210929165842.png)

## 六、项目展示

### 6.1 手机端数据收集

![image-20210929180207758](https://gitee.com/jiao_qianjin/zhishiku/raw/master/img/20210929180207.png)

![image-20210929180152215](https://gitee.com/jiao_qianjin/zhishiku/raw/master/img/20210929180153.png)



### 6.2 项目大屏展示

![](https://cos.jiaoqianjin.cn/jpg/clipboard_20210929_054532.png)
