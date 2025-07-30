## spring cloud demo 微服务demo 

### 项目结构
    -cloud-demo  <pom>
        -common  <jar>
        -services <pom> 
            -product-service <jar>
            -order-service   <jar>

### 父类pom文件 ,类型 pom
    dependencyManagement 管理版本号 使用 <type>pom</type> <scope>import</scope>
    定义好 springboot springcloud  Spring Cloud Alibaba 等版本号
    `
        <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!-- Spring Cloud -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!-- Spring Cloud Alibaba -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
    `

### 子类pom文件 
    dependencies 引入依赖
    services 中引入 微服务所需要依赖,引入common 公共依赖, 添加子模块(每个拆分的微服务 eg: 订单服务, 产品服务等)
    
    `
    <dependencies>
        <!-- Nacos 服务发现 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- Spring Cloud OpenFeign -->
        <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!-- Spring Cloud LoadBalancer -->
        <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-loadbalancer</artifactId>
        </dependency>
        <!-- Spring Cloud Circuit Breaker -->
        <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
        </dependency>

        <!-- Spring Cloud Nacos Config 配置中心 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>

        <!-- Spring Sentinel 熔断 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
    <dependencies>
        
     `


#### 微服务注册中心 Nacos
##### docker 本地启动 nacos 单机服务
```shell
# 拉取镜像 (我本机是 Mac M1 芯片，所以拉取的是 m1 版本)
docker pull zhusaidong/nacos-server-m1:2.0.3
# 创建持久化卷
docker volume create nacos-data
docker volume create nacos-logs
# 启动容器
docker run -d \
--name nacos-standalone \
-e MODE=standalone \
-e PREFER_HOST_MODE=hostname \
-e SPRING_DATASOURCE_PLATFORM=embedded \
-e NACOS_AUTH_ENABLE=true \
-e NACOS_APPLICATION_PORT=8848 \
-e NACOS_AUTH_TOKEN=WnCqDpsDkeBxwdYCZGJj4Fr9ij8jYegri+8iZN8MrIA= \
-e NACOS_AUTH_IDENTITY_KEY=WnCqDpsDkeBxwdYCZGJj4Fr9ij8jYegri+8iZN8MrIA= \
-e NACOS_AUTH_IDENTITY_VALUE=WnCqDpsDkeBxwdYCZGJj4Fr9ij8jYegri+8iZN8MrIA= \
-e SPRING_SECURITY_USER_PASSWORD=nacos \
-e JVM_XMS=512m \
-e JVM_XMX=512m \
-p 8848:8848 \
-p 9848:9848 \
-p 9849:9849 \
-v nacos-data:/home/nacos/data \
-v nacos-logs:/home/nacos/logs \
--restart=unless-stopped \
zhusaidong/nacos-server-m1:2.0.3

http://localhost:8848/nacos
首次使用默认账号：nacos/nacos
```

##### 各个微服务项目(springboot 项目)中 配置文件 配置 nacos 服务注册中心信息
见order-service 和product-service 项目的配置文件 yml 
配置文件优先级 配置中心 > 本地配置文件(properties > yml) > 环境变量 > 系统变量 > 默认配置
```

```
##### 各个微服务项目(springboot 项目)中 配置文件 配置 nacos 配置中心信息
    namespace (环境隔离)
    group (服务隔离)
    dataId (配置文件名称: common.properties, database.properties....)

springboot配置文件中 spring.config.import 导入List 配置中心的文件 eg: - nacos:common.properties?group=order-service-group&namespace=dev
                    spring.cloud.nacos.config 配置相关配置中心,配置文件等相关信息

    配置自动刷新: @RefreshScope + @Value("${xxx}")
                 @ConfigurationProperties 绑定参数 无感自动刷新
                 @NacosConfigManager 监听配置变化
    

##### OpenFeign 远程服务调用
    1. 引入依赖
    2. 配置文件中配置 openfeign 相关信息
    3. 启动类增加@EnableFeignClients 注解, 开启远程调用功能
    4. 编写接口,并使用 @FeignClient(value, url) 注解标识远程服务调用接口 
        1). value: 远程服务名称(服务管理注册中心的名字)
        2). url: 远程服务地址(可选,适用于第三方接口调用)
        3). fallback: 声明远程服务调用失败时,执行的方法, 如果方法出现异常,是不知道哪里出现异常,建议使用fallbackFactory
        4). fallbackFactory: 声明远程服务调用失败时,执行的方法,并可以获取失败原因
     
       接口中配合使用SpringMVC 注解, 对于注册中心的微服务远程调用, 将对应的服务的Controller层复制过来即可;
                                   对于第三方接口,根据接口API文档编写即可
     扩展使用:
         1. 配置文件中配置 openfeign 相关信息, 配置超时时间, 重试次数等 (或者直接创建相关的类交给Spring容器管理即可自动装配)
```java
            @Bean
            Logger.Level feignLoggerLevel() {
                return Logger.Level.FULL;
            }
```
         2. 日志(详细远程调用请求日志) logging.level配置具体FeignClient类的 日志级别, 注入Logeer.Level
         3. 拦截器(请求拦截器,响应拦截器) 实现Feign.RequestInterceptor接口 ResponseInterceptor接口,重写apply方法,
            并交给Spring容器管理即可自动装配 或者 直接在配置文件中配置
            eg:
```yaml
feign:
  client:
    config:
      product-service:  # 指定客户端名称
        read-timeout: 5000
        connect-timeout: 5000
        retryer: feign.Retryer.Default
        logger-level: FULL
        request-interceptors:
          - com.xjplus.intercepter.XTokenRequestInterceptor
```
        4. Fallback 兜底返回 (在服务不可用,或者调用出现异常时, 返回一个默认值, 让程序可以进行正常返回而不报错)
           需要配合Sentinel 服务限流使用, 配置文件中配置 sentinel 相关信息
```yaml
feign:
  sentinel:
    enabled: true
```
           自定义类 ProductFeignClientFallback 实现对应的远程调用FeignClient类, eg: ProductFeignClient, 且在该类上配置 fallback = ProductFeignClientFallback.class, 这种方式拿不到异常信息
           自定义类 ProductFeignClientFallbackFactory 实现 FallbackFactory<ProductFeignClient>, 重新 applay 方法 (这里可以捕获输出相关异常信息), 并返回一个 ProductFeignClientFallback 实例, 在对应的FeignClient类上配置 fallbackFactory = ProductFeignClientFallbackFactory.class, 这种方式可以拿到异常信息
   
##### Sentinel 服务限流
    1. 引入依赖
    2. 配置文件中配置 sentinel 相关信息
    

