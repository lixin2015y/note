package com.dubbo.consumer;


import com.dubbo.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        UserService userService = context.getBean(UserService.class);
        userService.sayHello("li xin ");
    }

}
