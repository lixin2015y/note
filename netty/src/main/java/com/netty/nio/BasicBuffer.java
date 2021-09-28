package com.netty.nio;

import sun.util.locale.InternalLocaleBuilder;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {

        // 穿创建一个Buffer，可以装5个int类型的值
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            // 写入Buffer
            intBuffer.put(i * 2);
        }
        // 读写翻转
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }




    }
}
