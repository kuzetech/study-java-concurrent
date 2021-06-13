package com.kuze.bigdata.study.l11designmodel;

import com.sun.xml.internal.ws.api.pipe.Fiber;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ThreadPerMessage {

    // 在java中频繁创建线程会导致程序OOM
    // 引入线程池又会增加开发复杂度
    // 只能严格控制线程池的数量，或者等待像go一样的协程技术 https://wiki.openjdk.java.net/display/loom/Main
    public static void main(String[] args) throws IOException {

        final ServerSocketChannel ssc =
        ServerSocketChannel.open().bind(
                new InetSocketAddress(8080));
        //处理请求
        try {
            while (true) {
                // 接收请求
                SocketChannel sc = ssc.accept();
                // 每个请求都创建一个线程
                new Thread(() -> {
                    try {
                        // 读Socket
                        ByteBuffer rb = ByteBuffer
                                .allocateDirect(1024);
                        sc.read(rb);
                        //模拟处理请求
                        Thread.sleep(2000);
                        // 写Socket
                        ByteBuffer wb =
                                (ByteBuffer) rb.flip();
                        sc.write(wb);
                        // 关闭Socket
                        sc.close();
                    } catch (Exception e) {
                        throw new UncheckedIOException((IOException) e);
                    }
                }).start();
            }
        } finally {
            ssc.close();
        }
    }

}
