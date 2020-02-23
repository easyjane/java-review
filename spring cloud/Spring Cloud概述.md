# Spring Cloud概述

- 微服务的意义

  - 简而言之，微服务架构风格是一种将单个应用程序开发为一套微服务的方法，每个微服务都在自己的进程中运行，并使用轻量级机制（通常是HTTP RESTFUL API）进行通信。 这些服务围绕业务功能构建，可通过全自动部署机制独立部署。 这些服务可以用不同的编程语言编写，并使用不同的数据存储技术，保证最低限度的集中管理。
  - 个人理解：
    - 单个应用程序按业务拆分成多个独立运行的程序（小型服务）。
    - 服务间可以用HTTP进行通信。
    - 可以自动化部署。
    - 可以使用不同的编程语言。
    - 可以使用不同的存储技术。


- 从Spring boot到Spring Cloud的过程

  - spring cloud是基于spring boot框架实现的一套微服务项目
  
- Spring boot与Spring Cloud版本的对应关系


  - https://spring.io/projects/spring-cloud 连接中可以找到

    | Release Train（spring cloud版本） | Boot Version（spring boot版本） |
    | --------------------------------- | ------------------------------- |
    | Hoxton                            | 2.2.x                           |
    | Greenwich                         | 2.1.x                           |
    | Finchley                          | 2.0.x                           |
    | Edgware                           | 1.5.x                           |
    | Dalston                           | 1.5.x                           |

### Spring Cloud Eureka 微服务注册中心的搭建

- #### Spring Cloud Eureka微服务注册中心服务端的搭建

  - 步骤

    1. 新增SpringBoot项目依赖，新增Spring Cloud项目依赖

       ```xml
       此配置文件在最外层pom中设置
       <dependencies>
           <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter</artifactId>
               <version>2.1.9.RELEASE</version>
           </dependency>
       </dependencies>
       
       
       <dependencyManagement>
           <dependencies>
               <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies -->
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-dependencies</artifactId>
                   <version>Greenwich.SR3</version>
                   <type>pom</type>
                   <scope>runtime</scope>
               </dependency>
       
           </dependencies>
       </dependencyManagement>
       
               
       ```

       

    2. 新增 spring-cloud-starter-netflix-eureka-server 依赖

       ```xml
       		<!-- 最新版的 eureka 服务端包 -->
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
                   <version>2.1.1.RELEASE</version>
               </dependency>
       ```

       

    3. 使用 @EnableEurekaServer 注解启动EurekaServer

       ```java
       @SpringBootApplication
       @EnableEurekaServer
       public class EurekaServerApplication {
           public static void main(String[] args) {
               SpringApplication.run(EurekaServerApplication.class);
           }
       }
       ```

       

    4. 配置application.yml文件

       ```yaml
       spring:
         application:
           name: eureka-server
       
       server:
         port: 8761
       
       
       eureka:
         server:
         client:
           service-url:
             defaultZone: http://localhost:8761/eureka
       ```

       

    5. 通过浏览器访问 http://localhost:8761

       > 启动的过程中会报gson的错误，只需要添加gson的依赖即可
       >
       > ```xml
       >  <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
       > <dependency>
       >     <groupId>com.google.code.gson</groupId>
       >     <artifactId>gson</artifactId>
       >     <version>2.8.5</version>
       > </dependency>
       > ```
       >
       > 

    

- #### Spring Cloud Eureka微服务注册中客户端的搭建

  - 步骤

    1. 添加 **spring-cloud-starter-netflix-eureka-client** 依赖

       ```xml
       <dependencies>
               <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client -->
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
                   <version>2.1.1.RELEASE</version>
               </dependency>
       
               <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
               <dependency>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-starter-web</artifactId>
                   <version>2.1.9.RELEASE</version>
               </dependency>
           </dependencies>
       ```

       

    2. 配置Eureka Server注册地址

       ```yaml
       spring:
         application:
           name: duan-eureka-client
       eureka:
         client:
           service-url:
             defaultZone: http://localhost:8761/eureka/
       
       ```

       

    3. 配置application

       ```java
       @SpringBootApplication
       public class EurekaClientApplication {
           public static void main(String[] args) {
               SpringApplication.run(EurekaClientApplication.class);
           }
       }
       ```

    4. 刷新 http://localhost:8761

### Spring Cloud Ribbon（负载均衡）开发实践

- #### ribbon架构分析图

  ![image-20200219140834509](C:\Users\duan\AppData\Roaming\Typora\typora-user-images\image-20200219140834509.png)

- #### ribbon基本配置

  1. 添加 spring-cloud-starter-netflix-ribbon项目依赖[A项目]

     > 如果项目依赖了Eureka，则不需要再依赖ribbon。
     >
     > 因为Eureka依赖于ribbon
  
     **client1 的 pom.xml的配置**
  
     ```xml
     <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client -->
             <dependency>
                 <groupId>org.springframework.cloud</groupId>
                 <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
                 <version>2.1.1.RELEASE</version>
             </dependency>
     
             <!-- ribbon服务 -->
             <dependency>
                 <groupId>org.springframework.cloud</groupId>
                 <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                 <version>2.1.1.RELEASE</version>
             </dependency>
     
             <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
             <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-web</artifactId>
                 <version>2.1.9.RELEASE</version>
             </dependency>
     ```
  
     **client1 的 application.yml的配置**
  
     ```yaml
     spring:
       application:
         name: duan-ribbon-a
     server:
       port: 6999
     
     eureka:
       client:
         service-url:
           defaultZone: http://localhost:8761/eureka/
     
     ```
  
  2. 使用RestTemplate+@LoadBalanced（负载均衡的注解）注释[A项目]
  
     **client1 的 application.java**
  
     ```java
     @SpringBootApplication
     public class RibbonApplication {
     
         @Bean
         @LoadBalanced
         public RestTemplate restTemplate() {
             return new RestTemplate();
         }
     
         public static void main(String[] args) {
             SpringApplication.run(RibbonApplication.class);
         }
     }
     ```
  
     **client1 的 contoller.java代码中只进行了简单的接口调用**
  
     ```java
     @RestController
     public class TestController {
     
         @Autowired
         private RestTemplate restTemplate;
     
         @GetMapping("/a")
         public Map<String ,Object> test1() {
             // 这里resttemplate的调用必须使用别名
             String url = "http://duan-ribbon-b/b";
             Map<String ,Object> map = restTemplate.getForObject(url,Map.class);
     
             return map;
         }
     }
     
     ```
  
     
  
  3. 使用http://为服务名/path url路径请求[A项目]
  
  4. 其他两个接口提供方的配置内容如下
  
     **client2的pom.xml文件(client3的pom.xml文件和此文件相同)**
  
     ```xml
     <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
             <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-web</artifactId>
                 <version>2.1.9.RELEASE</version>
             </dependency>
     
             <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
             <dependency>
                 <groupId>com.google.code.gson</groupId>
                 <artifactId>gson</artifactId>
                 <version>2.8.5</version>
             </dependency>
     
             <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client -->
             <dependency>
                 <groupId>org.springframework.cloud</groupId>
                 <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
                 <version>2.1.1.RELEASE</version>
             </dependency>
     
             <!-- ribbon服务 -->
             <dependency>
                 <groupId>org.springframework.cloud</groupId>
                 <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
                 <version>2.1.1.RELEASE</version>
             </dependency>
         </dependencies>
     ```
  
     **client2的application.yml文件**(client3的yml文件中只是port改为7002而已)
  
     ```yaml
     spring:
       application:
         name: duan-ribbon-b
     server:
       port: 7001
     
     eureka:
       client:
         service-url:
           defaultZone: http://localhost:8761/eureka/
     ```
  
     **client2的contoller.java文件**
  
     ```java
     @RestController
     public class TestController {
     
         @RequestMapping("/b")
         public Map<String,Object> b() {
             Map map = new HashMap();
             map.put("date",System.currentTimeMillis());
             // client2的port为7001
             // client3的port为7002
             map.put("port","7001");
     
             return map;
         }
     }
     ```
  
  
  
### Spring Cloud Hystrix 断路器

> 简单来说就是阻隔服务因为调用多个服务接口的时候，其中某一个接口奔溃而导致的全盘崩溃。
>
> **比方一个小区的电力供应：都会在小区每个单元设置一个总闸开关，每层楼一个总闸，每一户蛇者一个总闸，因此在单独某一户断电的情况下，不至于影响到整体的单元甚至小区**

#### Spring Cloud Hystrix 设计原理

> Hystrix是由Netflix开源的一个延迟和容错库，用于隔离访问远程系统、服务或者第三方库，防止级联失败，从而提升系统的可用性、容错性与局部应用的弹性，是一个实现了超时机制和断路器模式的工具类库。

- 防止任何单独的依赖耗尽资源（线程）
  过载立即切断并快速失败，防止排队
- 尽可能提供回退以保护用户免受故障
- 使用隔离技术（例如隔板，泳道和断路器模式）来限制任何一个依赖的影响
- 通过近实时的指标，监控和告警，确保故障被及时发现
- 通过动态修改配置属性，确保故障及时恢复
- 防止整个依赖客户端执行失败，而不仅仅是网络通信

#### Spring Cloud Hystrix的工作原理

> 当使用Hystrix封装每个基础依赖项时,每个依赖项彼此隔离，受到延迟时发生饱和的资源的限制，并包含回退逻辑，该逻辑决定了在依赖项中发生任何类型的故障时做出什么响应。

- 使用命令模式将所有对外部服务（或依赖关系）的调用包装在HystrixCommand或HystrixObservableCommand对象中，并将该对象放在单独的线程中执行。
- 每个依赖都维护着一个线程池（或信号量），线程池被耗尽则拒绝请求（而不是让请求排队）。
- 记录请求成功，失败，超时和线程拒绝。
- 服务错误百分比超过了阈值，熔断器开关自动打开，一段时间内停止对该服务的所有请求。
- 请求失败，被拒绝，超时或熔断时执行降级逻辑。
- 近实时地监控指标和配置的修改。

#### Spring cloud Hystrix操作

1. 添加 **spring-cloud-starter-netflix-hystrix** 项目依赖

   ```xml
   <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-hystrix -->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
               <version>2.1.1.RELEASE</version>
           </dependency>
   ```

   

2. 主启动类添加@EnableCircuitBreaker注解

   ```java
   @SpringBootApplication
   @EnableCircuitBreaker
   public class HystrixApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(HystrixApplication.class);
       }
   }
   ```

   

3. 控制器类使用@HystrixCommand注解

   ```java
   @RestController
   public class HystrixController {
   
       @RequestMapping("/hello")
       @HystrixCommand(fallbackMethod = "hystrixHello")
       public String hello() {
           throw new RuntimeException("主动报错");
       }
   
       public String hystrixHello() {
           return "这里是hystrix hello world";
       }
   }
   ```

4. 访问地址

   > http://localhost:8888/hello 则控制台会显示这里是hystrix hello world
   >
   > **我在yml文件中将server.port修改为了8888**

  #### Spring Cloud Hystrix合并请求

> 将一定时间内的请求的多个合并为一个
>
> ![image-20200220132743704](C:\Users\duan\AppData\Roaming\Typora\typora-user-images\image-20200220132743704.png)

1. 调用方法需要使用初始化HystrixRequestContext

   ```java
   @RequestMapping("/list")
   public List<String> list() throws ExecutionException, InterruptedException {
   
       HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
   
       Future<String> storage1 = hystrixService.getStorage(new Random().nextInt() + "");
       // 当没有当前线程睡眠的情况下，下方service服务指挥请求一次，并且返回结果
       // Thread.sleep(2000L);
       Future<String> storage2 = hystrixService.getStorage(new Random().nextInt() + "");
   
       List<String> list = new ArrayList<String>();
       if (storage1 == null || storage2 == null) {
           logger.error("没有值，走了没合并的方法");
           return list ;
       }
   
       list.add(storage1.get());
       list.add(storage2.get());
       logger.info("走了合并方法返回值："+ Arrays.toString(list.toArray()));
       return list;
   }
   ```

   

2. 需要合并的方法使用注解@HystrixCollapser

   ```java
   @HystrixCollapser(batchMethod = "getStorageList",collapserProperties = {
               @HystrixProperty(name = "timerDelayInMilliseconds",value = "1000")
       })
   public Future<String> getStorage(String id) {
       return null;
   }
   ```

   

3. 实际合并方法使用注解@HystrixCommand

   ```java
   @HystrixCommand
   public List<String> getStorageList(List<String> idList) {
       logger.info("getStorageList方法的传入参数是"+idList.size()+"个，内容分别是："+ Arrays.toString(idList.toArray()));
       List<String> result = new ArrayList<String>();
       for (String id :
            idList) {
           result.add("id:"+id + "结果："+new Random().nextInt());
       }
       return result;
   }
   ```



### Spring Cloud Feign 简介

> Feign是一个声明式的微服务调用客户端

#### 什么是Feign？
> Feign 的英文表意为“假装，伪装，变形”， 是一个http请求调用的轻量级框架，可以以Java接口注解的方式调用Http请求，而不用像Java中通过封装HTTP请求报文的方式直接调用。Feign通过处理注解，将请求模板化，当实际调用的时候，传入参数，根据参数再应用到请求上，进而转化成真正的请求，这种请求相对而言比较直观。
>  Feign被广泛应用在Spring Cloud 的解决方案中，是学习基于Spring Cloud 微服务架构不可或缺的重要组件。
>  开源项目地址：
>  [https://github.com/OpenFeign/feign](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FOpenFeign%2Ffeign)

#### Feign解决了什么问题？
> **封装了Http调用流程，更适合面向接口化的变成习惯**
>  在服务调用的场景中，我们经常调用基于Http协议的服务，而我们经常使用到的框架可能有HttpURLConnection、Apache HttpComponnets、OkHttp3 、Netty等等，这些框架在基于自身的专注点提供了自身特性。而从角色划分上来看，他们的职能是一致的提供Http调用服务。





### Spring Cloud Zuul 微服务网关

> 微服务网管是微服务对外服务的窗口，执行内外隔离
>
> Spring Cloud Zuul 就是对应的微服务网管解决方案
>
> 微服务都是在内网，没有加安全控制，无状态（没有登陆，没有注册）的应用

#### Spring Cloud Zuul部署的架构图

Zuul 网管搭建过程需要三套系统

1. Eureka微服务注册中心
2. Zuul网管系统
3. A微服务应用

> 备注：
>
> - 项目场景为Zuul网管系统 ==> A微服务
> - A微服务都需要注册Eureka
> - Zuul服务需要注册到Eureka，需要使用 **@EnableZuulProxy** 注解
>
> ![image-20200222002308495](C:\Users\duan\AppData\Roaming\Typora\typora-user-images\image-20200222002308495.png)

#### Spring Cloud Zuul 项目的搭建过程

1. 引入maven依赖

   ```xml
   <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-zuul -->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
       <version>2.1.1.RELEASE</version>
   </dependency>
   <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-client -->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
       <version>2.1.1.RELEASE</version>
   </dependency>
   
   <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
   <dependency>
       <groupId>com.google.code.gson</groupId>
       <artifactId>gson</artifactId>
       <version>2.8.5</version>
   </dependency>
   <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
       <version>2.1.9.RELEASE</version>
   </dependency>
   ```

2. 在主启动类中添加注解 **@EnableZuulProxy**

   ```java
   @SpringBootApplication
   @EnableZuulProxy
   public class ZuulApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(ZuulApplication.class);
       }
   }
   ```

3. 在 **application.yml** 中将zuul注册到注册中心

   ```yaml
   spring:
     application:
       name: duan-zuul-client
   
   server:
     port: 9003
   
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka/
   ```

4. 修改A微服务的普通方法

   > A微服务只是一个可以注册到Eureka注册中心的普通微服务
   >
   > 此方法也是普通的方法而已

   ```java
     @GetMapping("/zuul/test")
       public Map<String ,Object> zuulTest() {
           Map<String ,Object> map = new HashMap<String, Object>();
   
           map.put("result","zuul-test");
           return map;
       }
   ```

   

5. 依次启动 注册中心Eureka、A微服务、Zuul微服务网关项目

   > 访问地址：http://localhost:9003/duan-ribbon-a/zuul/test
   >
   > http://Zuul微服务网关项目/A微服务/A微服务中暴露的方法
   >
   > 就可以访问到A微服务的方法获取相对应的内容