package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7777);
        // 绑定端口并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        // 创建buffer数组
        ByteBuffer[] byteBufferArr = new ByteBuffer[2];
        byteBufferArr[0] = ByteBuffer.allocate(5);
        byteBufferArr[1] = ByteBuffer.allocate(3);

        // 假定从客户端接收8个字节
        int messageLength = 8;

        // 有客户端进行连接，则创建一个SocketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long read = socketChannel.read(byteBufferArr);
                byteRead += read;
                System.out.println("读取到的" + byteRead);
                // 使用流打印，看看当前Buffer有什么数据
                Arrays.stream(byteBufferArr)
                        .map(byteBuffer -> "position:" + byteBuffer.position() + "---->limit:" + byteBuffer.limit())
                        .forEach(System.out::println);
            }

            // 将所有buffer进行翻转
            Arrays.stream(byteBufferArr).forEach(Buffer::flip);

            // 回显到客户端
            int writeByteLength = 0;
            while (writeByteLength < messageLength) {
                long write = socketChannel.write(byteBufferArr);
                writeByteLength += write;
            }

            // 复位
            Arrays.stream(byteBufferArr).forEach(Buffer::clear);

            System.out.println("byteRead = " + byteRead);
            System.out.println("writeByteLength = " + writeByteLength);
        }

    }
}
