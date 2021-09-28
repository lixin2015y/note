package com.netty.bio;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import javax.smartcardio.TerminalFactory;
import java.beans.beancontext.BeanContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");

        while (true) {
            // 等待链接
            System.out.println("等待链接。。。。。。");
            Socket socket = serverSocket.accept(); // 此处会进行阻塞
            System.out.println("链接到一个客户端");

            executorService.execute(() -> {
                // 和客户端进行通信
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void handler(Socket socket) throws IOException {

        System.out.println("当前线程名称：" + Thread.currentThread().getName() + "ID:" + Thread.currentThread().getId());
        byte[] bytes = new byte[1024];
        // 获取流
        InputStream inputStream = socket.getInputStream();


        System.out.println("等待客户端发送消息。。。。。。");
        // 循环读取
        while (true) {
            int read = inputStream.read(bytes); // 流的读取会进行阻塞，如果没有数据的情况下
            if (read != -1) {
                System.out.println(new String(bytes, 0, read));
            } else {
                break;
            }
        }

    }
}
