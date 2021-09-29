package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);

        // 设置服务端IP端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        // 链接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("链接需要时间，客户端非阻塞，可以做其他工作");
            }
        }

        // 如果成功链接，发送数据
        String str = "hello 尚硅谷····";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据
        socketChannel.read(byteBuffer);
        System.in.read();
    }
}
