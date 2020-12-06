package com.longwanli.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("consumerController")
public class ConsumerController {

    //自动转载，所以直接用
    @Autowired
    private DiscoveryClient discoveryClient;

    //不会自动装载，不能直接用
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 返回provider工程的实例
     *
     * @return
     */
    @GetMapping("/instances")
    public List<ServiceInstance> instances() {
        List<ServiceInstance> provider = this.discoveryClient.getInstances("provider");
        //provider实例对象：[{"serviceId":"provider","host":"192.168.239.1","port":8843,"secure":false,"metadata":{"nacos.instanceId":"192.168.239.1#8843#DEFAULT#DEFAULT_GROUP@@provider","nacos.weight":"1.0","nacos.cluster":"DEFAULT","nacos.healthy":"true","preserved.register.source":"SPRING_CLOUD"},"uri":"http://192.168.239.1:8843","scheme":null,"instanceId":null}]
        return provider;
    }

    /**
     * 负载均衡模拟：启动多个provider实例，调用provider服务的index方法，多次请求，随机调用
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        //1、得到provider服务的全部实例
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        //生成0-list.size()间的随机数
        int index = ThreadLocalRandom.current().nextInt(list.size());
        System.out.println("index:" + index);
        ServiceInstance serviceInstance = list.get(index);
        //getUri结果形式为：http://192.168.239.1:8843
        String url = serviceInstance.getUri() + "/providerController/index";
        log.info("url为：{}", url);
        //2、调用
        String string = "调用了端口为：" + serviceInstance.getPort() + "的服务；返回结果是：" + restTemplate.getForObject(url, String.class);
        return string;
    }

    /**
     * 在注入restTemplate的方法上加上@LoadBalanced注解，调用内置的负载均衡算法（默认为轮询算法，即依次调用）
     *
     * !!!!!!!!!注意：用了 @LoadBalanced注解后，restTemplate对象访问接口时，URL不能再用具体的String形式请求地址，只能用服务名代替URI。
     *     因为要隐藏端口，用负载均衡算法决定使用哪个端口。
     *
     * @return
     */
    @GetMapping("/index2")
    public String index2() {
        //由于使用了@LoadBalanced，故可以用服务名provider代替URI，此时不出现端口，有算法决定使用哪个端口号
        return this.restTemplate.getForObject("http://provider/providerController/index", String.class);
    }

}
