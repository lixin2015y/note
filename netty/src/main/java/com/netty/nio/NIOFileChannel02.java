package com.netty.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过FileChannel读取本地文件
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {

        // 创建文件输入流
        FileInputStream inputStream = new FileInputStream("D://a.txt");
        // 创建通道
        FileChannel fileChannel = inputStream.getChannel();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 从通道中读数据
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        // 显示缓冲区中的数据
        System.out.println(new String(byteBuffer.array()));
    }
}
