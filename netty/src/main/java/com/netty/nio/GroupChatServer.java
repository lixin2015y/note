package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    /**
     * 初始化工作
     */
    public GroupChatServer() throws IOException {
        this.selector = Selector.open();
        this.listenChannel = ServerSocketChannel.open();
        // 绑定端口
        listenChannel.socket().bind(new InetSocketAddress(PORT));
        listenChannel.configureBlocking(false);
        // 注册
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() {
        try {
            // 循环监听
            while (true) {
                int selectedCount = selector.select(2000);
                if (selectedCount > 0) {
                    // 有事件，进行处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        // 监听不同事件
                        if (selectionKey.isAcceptable()) {
                            // 链接事件
                            SocketChannel accept = listenChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() + "上线了");
                        }

                        if (selectionKey.isReadable()) {
                            // 读事件，通道可读,处理读
                            readClientMessage(selectionKey);
                        }
                        // 删除key
                        iterator.remove();
                    }
                } else {
//                    System.out.println("无事件，等待中。。。。。。。。。。。。。。。");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取客户端消息
     */
    public void readClientMessage(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel)key.channel();
        channel.configureBlocking(false);
        try {
            // 创建缓冲
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = channel.read(byteBuffer);
            if (read > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端:" + msg);
                // 向其他客户端转发消息
                sendMessageToOther(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 向其他通道发送消息，排除自己
     * @param msg
     * @param self
     */
    public void sendMessageToOther(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中、。。。。。。。");
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            Channel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatServer server = new GroupChatServer();
        server.listen();

    }
}
