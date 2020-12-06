package com.longwanli.consumer.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ribbon负载均衡的权重算法配置
 */
@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        //读取配置文件
    }

    @Override
    public Server choose(Object o) {
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) loadBalancer;
        //获取要请求的微服务名称，这里的微服务名是provider
        String name = baseLoadBalancer.getName();
        System.out.println("name:" + name);//name:provider
        //获取服务发现的相关API
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        try {
            //获取实例方法中包含着权重算法
            Instance instance = namingService.selectOneHealthyInstance(name);
            log.info("选择的实例是port={},instance={}", instance.getPort(), instance);
            //结果形式：选择的实例是port=8083,instance={"clusterName":"DEFAULT","enabled":true,"ephemeral":true,"healthy":true,"instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000,"instanceId":"192.168.239.1#8083#DEFAULT#DEFAULT_GROUP@@provider","ip":"192.168.239.1","ipDeleteTimeout":30000,"metadata":{"preserved.register.source":"SPRING_CLOUD"},"port":8083,"serviceName":"DEFAULT_GROUP@@provider","weight":1.0}
            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }
    }
}