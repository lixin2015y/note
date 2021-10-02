package com.netty.nio.zero.copy;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.LockInfo;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ZeroCopy {
    @Test
    public void test1() throws IOException {
        File file = new File("1.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket accept = serverSocket.accept();
        byte[] bytes = new byte[1023];
        randomAccessFile.read(bytes);
        accept.getOutputStream().write(bytes);
    }

    /**
     * NIO服务器端
     */
    @Test
    public void testNIO() throws IOException {

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        int readCount = 0;
        while (readCount != -1) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            try {
                readCount = socketChannel.read(byteBuffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byteBuffer.rewind();
        }
    }


    /**
     * NIO客户端
     */
    @Test
    public void test2() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        final String fileName = "1.txt";
        // 得到文件channel
        FileInputStream fileInputStream = new FileInputStream(fileName);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        long startTime = System.currentTimeMillis();
        long transferCount = fileInputStreamChannel.transferTo(0, fileInputStreamChannel.size(), socketChannel);
        long endTime = System.currentTimeMillis();

        System.out.println("发送总字节数" + transferCount + "耗时：" + (endTime - startTime) + "毫秒");

    }
}

