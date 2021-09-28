package com.netty.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个Buffer实现对文件进行copy
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        File file = new File("1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");

        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {
            byteBuffer.clear();
            int read = fileInputStreamChannel.read(byteBuffer);
            if (read == -1) { // 表示读取完毕
                break;
            }
            // 将buffer中的数据写入到outChannel中
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
        }

        fileOutputStream.close();
        fileInputStream.close();

    }
}
