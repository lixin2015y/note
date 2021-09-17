package com.dubbo.provider.impl;


import com.dubbo.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    public String sayHello(String name) {
        System.out.println("hi" + name + "hello");
        return "hello" + name;
    }
}
