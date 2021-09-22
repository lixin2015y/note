package com.dubbo.consumer;


import com.dubbo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.sayHello(""));
    }

    /**
     * 使用配置类进行配置
     */
    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.sayHello("使用配置类进行配置"));


    }

}
