# Spring Cloud LoadBalancer 实现在多集群部署中总是访问本地集群服务

方案想法来源于这篇[博客](https://cloud.tencent.com/developer/article/1927033)。

场景上看是有一套注册中心，管理多个集群服务，每个集群的上游服务只访问本集群的下游服务。

实现原理：注册中心服务实例**元数据信息** + SpringCloud LoadBalancer **自定义 ServiceInstanceListSupplier**；
即在获取服务实例列表后执行负载均衡规则前，额外插入一步校验服务实例元数据信息中的集群信息是否和当前发起请求的服务的集群信息一致。

> 其实Nacos已经实现了总是访问本地集群服务的功能。旧版本starter使用Ribbon做负载均衡器的时候，Nacos通过**NacosRule**实现，不过现在Ribbon已经废弃了，使用LoadBalancer做负载均衡器则使用 **NacosLoadBalancer** 实现（看源码是借助服务实例[ServiceInstance]的元数据 `nacos.cluster`实现的）。
>
> 只不过这个 NacosLoadBalancer 默认没有启用，默认使用的是 RoundRobinLoadBalancer。可以借助 @LoadBalancerClient 注解指定优先加载 NacosLoadBalancer 替代 RoundRobinLoadBalancer（原理上是通过这个注解修改了提升了 NacosLoadBalancer 加载优先级）。DEMO源码：SpringBoot-Labs/loadbalancer-ribbon/spring-cloud-loadbalancer-demo02-consumer
>
> ```java
> @LoadBalancerClients(defaultConfiguration = NacosLoadBalancerClientConfiguration.class)
> ```
>
> NacosLoadBalancer 总是访问本地集群服务的原理：
>
> ```java
> //NacosLoadBalancer#getInstanceResponse() 中有这么一段就是校验集群信息的
> List<ServiceInstance> sameClusterInstances = (List)serviceInstances.stream().filter((serviceInstance) -> {
>     String cluster = (String)serviceInstance.getMetadata().get("nacos.cluster");
>     return StringUtils.equals(cluster, clusterName);
> }).collect(Collectors.toList());
> ```
>
> 另外 spring-cloud-starter-alibaba-nacos-discovery 源码并没有找到与 LoadBalancerZoneConfig 相关的操作，推测 Nacos 不支持通过 LoadBalancerZoneConfig 的方式实现总是访问本地集群。

由于博客原文的源码已经访问不了了，这里按其说法，使用 Nacos 和 LoadBalancer 重新改造下，如果熟悉 LoadBalancer 源码实现也比较简单。

**前提**：

+ **熟悉 Nacos 元数据配置**

  Nacos元数据分为服务元数据、实例元数据和集群元数据，这里使用**实例元数据**传递集群信息，其实也可以使用**集群名**因为看nacos源码发现已经将集群信息以及加到ServiceInstance的metadata一起返回了，另外也可以使用**集群元数据**无非参考源码怎么将集群元数据也一起添加到ServiceInstance的metadata一起返回。

  ```properties
  instances = {ArrayList@10777}  size = 2
   0 = {NacosServiceInstance@10810} 
    serviceId = "demo-provider"
    instanceId = null
    host = "192.168.2.169"
    port = 10463
    secure = false
    metadata = {HashMap@10813}  size = 6
     "nacos.instanceId" -> null
     "nacos.weight" -> "1.0"
     "nacos.cluster" -> "BJ"	//这里可以看到实例元数据信息中包含了集群名信息，即spring.cloud.nacos.discovery.cluster-name 指定的
     "nacos.ephemeral" -> "true"
     "nacos.healthy" -> "true"
     "preserved.register.source" -> "SPRING_CLOUD"
   1 = {NacosServiceInstance@10811} 
  ```

  > Nacos 服务领域模型：命名空间（比如开发、测试、生产） -> 分组（比如电商） -> 服务Service（比如商品服务） -> 集群Cluster -> 实例Instance。

+ **熟悉 LoadBalancer 源码 ServiceInstanceListSupplier 及 负载均衡规则配置原理** （难点）

  业务很简单，难点在于怎么自定义 ServiceInstanceListSupplier 及 负载均衡规则，并优雅地嵌入到框架代码逻辑中；比如：ServiceInstanceListSupplier Bean 其实是一串 Supplier 用装饰器模式层层装饰组织起来的（像套娃），怎么将这个集群过滤的 Supplier 合理的加入原本的业务。

**实现**：

1. **先创建一个服务4个实例，分两个集群**

   通过虚拟机参数修改集群信息（更接近线上实际方式，多用虚拟机参数或虚拟机参数指定profile），比如 `BJ` `TJ`。

   ```shell
   -Dspring.cloud.nacos.discovery.cluster-name=BJ
   -Dspring.cloud.nacos.discovery.cluster-name=TJ
   ```

2. **自定义 ServiceInstanceListSupplier 和 负载均衡规则**

   自定义的ServiceInstanceListSupplier 最好是插入到 **CachingServiceInstanceListSupplier** 和   **DiscoveryClientServiceInstanceListSupplier** 中间，ServiceInstanceListSupplier 官方实现类有好几个，随便参考哪一个实现都可以。

   这里就使用上面的 "nacos.cluster" 元数据信息，在DiscoveryClientServiceInstanceListSupplier获取到全部服务实例列表后，再经过自定义的 ServiceInstanceListSupplier 校对 NacosServiceInstance元数据 "nacos.cluster" 信息过滤出同集群的服务实例列表，然后再由 CachingServiceInstanceListSupplier 缓存。

DEMO源码：SpringBoot-Labs/loadbalancer-ribbon/spring-cloud-loadbalancer-demo01-consumer













