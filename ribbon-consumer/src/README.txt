@LoadBalanced
用来标注RestTemplate,使用LoadBalancerClient来配置他，
LoadBalancerAutoCOnfiguration为负责均衡自动化配置类，两个条件：
1 RestTemplate 必须存在，
2 LoadBalancerclient的实现Bean 必须存在，
做了三件事：
1.创建LoadBalanceInterceptor的bean，用于实现对客户端发起请求的拦截；
2.创建RestTemplateCustomizer的bean，给RestTemplate增加LoadBalanceInterceptor拦截器；
3.维护一个被@LoadBalanced注解修饰的RestTemplate对象列表，并在这里进行初始化，通过RestTemplateCustomizer的实例来RestTemplate增加LoadBalanceInterceptor拦截器；


当一个被@LoadBalanced注解的RestTemplate对象向外发起HTTP请求时，会被LoadBalanceInterceptor的intercept方法拦截，获取URI对象中的服务名，然后调用execute函数根据服务名来选择实例；
