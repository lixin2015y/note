package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创见
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 创建一个selector
        Selector selector = Selector.open();

        // 绑定端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 本身的ServerSocketChannel也要注册到Selector上去,关心事件为创建连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待一秒钟，无连接");
                continue;
            }

            // 如果返回的数据>0，获取到有事件发生的selectionKey集合（关注事件）
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 通过selectionKeys获取通道
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                // 获取一个selectionKey
                SelectionKey selectionKey = iterator.next();
                // 根据key对应的通道发生的事件，
                if (selectionKey.isAcceptable()) {
                    // 此处accept方法并不会阻塞，因为已经知道他是acceptable了
                    SocketChannel accept = serverSocketChannel.accept();
                    // 设置为非阻塞
                    accept.configureBlocking(false);
                    System.out.println("客户端连接成功，生成了一个SocketChannel" + accept.hashCode());

                    // 将通道注册到selector上，并绑定一个Buffer
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    accept.register(selector, SelectionKey.OP_READ, byteBuffer);
                }

                if (selectionKey.isReadable()) {
                    // 读事件
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    // 通过selectorKey获取到Buffer
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端" + new String(buffer.array()));

                }

                // 手动删除当前sectionKey
                iterator.remove();
            }

        }

    }
}
