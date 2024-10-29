# 系统监控

## 系统监控的简介

### 系统监控的指标

主要分为下面一些指标：

+ 系统资源占用

  + CPU
  + 内存
  + 磁盘
  + 带宽

+ 业务日志

  + 业务埋点

    常用于业务数据统计，用来研究用户习惯、查看营销收入等等。

  + 异常调用链路

+ 性能指标

  + TPS
  + QPS
  + 接口响应时间

+ JVM监控

  + Java 进程粒度的系统资源占用
  + GC 垃圾回收时间
  + JVM 内线程状态
  + ...

### 系统监控的架构

**基本都包括下面几种结构**：

+ **数据收集**

  比如：LogStash、Flume、Skywalking 的 Agent。

+ **消息总线**

  数据收集器收集的数据可能通过消息总线传输到数据中心，比如 LogStash 收集的数据经常借助 Kafka 传给数据中心，当然也可以用其他方式实现，比如其他消息队列、Redis 发布订阅、Guava消息总线。

+ **数据中心**

  数据整合、处理、持久化等。

+ **监控平台（UI）**

  用于数据搜索、展示等。

+ **告警管理器**

  用于通过 邮件、短信、企业微信等告警，告警这部分功能也可能位于监控平台配置，比如 Grafana 就包含告警模块。

比如 Prometheus 的监控系统架构：

![](https://prometheus.io/assets/architecture.png)

又比如 Skywalking 的监控系统架构：

![](https://skywalking.apache.org/images/home/architecture_2160x720.png)

**流行的系统监控方案**：

+ **ELK** (LogStash + ElasticSearch + Kibana)

  + LogStash 负责数据采集，作为组件嵌入到业务服务
  + ES 作为数据中心
  + Kibana 作为监控平台

+ **Prometheus + Grafana**

  + 一些支持了 Prometheus 上报接口的第三方采集器负责数据采集，比如 micrometer-registry-prometheus。

    生产环境大概率默认的数据采集器无法满足业务需要，可能需要定制指标进行监控。

    关于 Prometheus 数据采集器工作原理以及如何实现自定义指标监控，参考 [monitor-reporter.md](./monitor-reporter.md)。

  + Prometheus 作为数据中心

  + Grafana 作为监控平台

    Prometheus 本身也提供了 UI 页面，提供了 [PromQL](https://prometheus.io/docs/prometheus/latest/querying/basics/) 用于查询和聚合数据并以图表的方式进行展示。

    [PromQL Examples](https://prometheus.io/docs/prometheus/latest/querying/examples/)

    但是可视化能力比较弱，通常使用 Grafana 实现数据的可视化。

    [Grafana 图表库](https://grafana.com/grafana/dashboards/) (别人做好的图表) 可以直接导入 Grafana 使用。

  + 告警

    可以选择配置 Prometheus AlertManager，或者在 Grafana 中配置告警。

  [快速入门](https://www.cnblogs.com/chanshuyi/category/1862951.html) 都是一些配置，网上教程很多，这里随便参考一个即可，详细内容还是去看官网。

+ **Skywalking**

  + 提供了 Agent 用于数据采集，比如 Java Agent 实际上是使用的 JVMTI 接口，在字节码层面织入监控数据上报逻辑。
  + 提供了 OAP (Observability Analysis Platform，可观测的分析平台) 作为数据中心。
  + 提供了 Skywalking UI 作为监控平台，Skywalking 也支持 Grafana 、 Booster UI 等其他组件作为监控平台。

+ Zipkin

  现在已经不怎么流行了，忽略。

## 参考

+ [Prometheus + Grafana 快速入门](https://www.cnblogs.com/chanshuyi/category/1862951.html)
+ [芋道 Prometheus + Grafana + Alertmanager 极简入门](https://www.iocoder.cn/Prometheus/install/)