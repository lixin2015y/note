package com.netty.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过FileChannel将字符写入到文件中去
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {

        String text = "hello 尚硅谷";
        // 创建输出流->包装到fileChannel
        FileOutputStream fileOutputStream = new FileOutputStream("d://a.txt");
        // 创建Channel
        FileChannel channel = fileOutputStream.getChannel();
        // 创建一个缓冲
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 将字符写入到buffer
        buffer.put(text.getBytes());
        // 对buffer进行翻转
        buffer.flip();
        // 写入到Channel
        channel.write(buffer);
        // 关闭流
        fileOutputStream.close();
    }
}
