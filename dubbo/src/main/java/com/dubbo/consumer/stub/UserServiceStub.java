package com.dubbo.consumer.stub;

import com.dubbo.service.UserService;

/**
 * 消费者端本地存根，
 */
public class UserServiceStub implements UserService {

    private UserService userService;

    public UserServiceStub(UserService userService) {
        this.userService = userService;
    }

    public String sayHello(String name) {
        if (name == null) {
            return "hello 无名";
        }

        return userService.sayHello(name);
    }

}
