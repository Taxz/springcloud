spring.application.name=api-gateway
server.port=5555

zuul.routes.api-a.path=/api-a/**
zuul.routes.api-a.serviceId=hello-service
#
#zuul.routes.api-b.path=/api-b/**
#zuul.routes.api-b.serviceId=feign-consumer

eureka.client.serviceUrl.defaultZone=http://localhost:1111/eureka/

#传统的路由配置

#--单实例配置
#    zuul.routes.user-service.path=/user-service/**
#    zuul.routes.user-service.serviceId=http://localhost:11111/

# 多实例配置
#    zuul.routes.user-service.path=/user-service/**
#    zuul.routes.user-service.serviceId=user-service
#    ribbon.eureka.enabled=false
#    user.service.ribbon.listOfServers=http://localhost:8080/,http://localhost:8081

#服务路由配置
#   zuul.routes.user-service.path=/user-service/**
#   zuul.routes.user-service.serviceId=user-service
#以上两行类似于
#   zuul.routes.user-service=/user-service/**

#原理：通过eureka将自身注册为服务，并获取eureka中的实例清单，当请求访问API网关时，根据请求路径匹配path，知道在具体的serviceId，网关已经serviceId对应的服务清单地址，
#只需通过负载均衡策略，直接在这些清单中选择一个具体实例转发就能完成路由工作，

#默认情况下，会为所有实例创建默认路由规则，path会使用serviceId配置的服务名作为请求前缀，

#使用 zuul.ignored-services=服务名，来设置不自动创建路由规则

#路径匹配 按照保存的路径顺序进行匹配，保存的顺序取决于配置文件的配置路径的顺序，properties文件无法保证顺序，应该使用yaml文件，以确保顺序

#忽略表达式参数
#zuul.ignored-patterns=/**/hello/**

#请求转发 到本地资源
#zuul.routes.api-b.path=/api-b/**
#zuul.routes.api-b.url=forward:/local

#Cookie 配置
#设置全局参数为空 覆盖默认值，不推荐
#zuul.sensitive-headers=
#通过指定路由的参数配置
#方法1，对指定路由开启自定义敏感头
#zuul.routes.<router>.customSensitiveHeaders=true

#方法2：将指定路由的敏感头信息设置为空
#zuul.routes.<router>.sensitiveHeaders=

#解决重定向问题
