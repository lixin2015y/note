package com.dubbo.provider.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.dubbo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@EnableDubbo
@ComponentScan("com.dubbo.provider.*")
@Configuration
public class ProviderConfig {


    @Bean
    ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("application-provider");
        return applicationConfig;
    }

    @Bean
    RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("127.0.0.1:2181");
        registryConfig.setProtocol("zookeeper");

        return registryConfig;
    }

    @Bean
    ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setHost("20880");
        protocolConfig.setName("dubbo");
        return protocolConfig;
    }

    @Bean
    ServiceConfig<UserService> serviceConfig(UserService userService) {
        ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(userService);

        // 配置method
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("sayHello");

        // 将method设置到serviceConfig中
        serviceConfig.setMethods(Arrays.asList(methodConfig));
        return serviceConfig;
    }
}
