package com.netty.nio;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO
 *
 * @className: ZeroCopy
 * @author: li xin
 * @date: 2021-09-30
 **/
public class ZeroCopy {

    /**
     * 传统的将一个文件发送到网络中去
     */
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
}
