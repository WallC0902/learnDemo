package com.example.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wangchangpeng on 2019/9/25.
 */
public class BIOServer {


    /**
     * 主方法
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务端启动了....");
        while (true) {
            Socket accept = server.accept();
            System.out.println("出来接客了....");
            InputStream inputStream = accept.getInputStream();
            byte[] bytes = new byte[1024];
            while (true) {
                int data = inputStream.read(bytes);
                if (data != -1) {
                    String info = new String(bytes, "utf-8");
                    System.out.println(info);
                } else {
                    break;
                }
            }
        }
    }
}
