package com.netty.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception{
        RandomAccessFile file = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = file.getChannel();
        /**
         * FileChannel.MapMode.READ_WRITE 读写模式
         * 0  可以直接修改的起始位置
         * 5  可以将1.txt的多少个字节映射到堆外内存，即可以修改的部分(长度大小而不是索引位置)
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte) 'H');
        map.put(3, (byte) '9');

        channel.close();

    }
}
