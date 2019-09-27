package com.example.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by wangchangpeng on 2019/9/25.
 */
public class NIOServer {

        public static void main(String[] args){
            new NIOServer(8080).start();
        }

        private int port;
        private Selector selector;
        private ExecutorService service = Executors.newFixedThreadPool(5);

        public NIOServer(int port) {
            this.port = port;
        }

        public void init() {
            ServerSocketChannel ssc = null;
            try {
                ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                ssc.bind(new InetSocketAddress(port));
                selector = Selector.open();
                ssc.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("NioServer started ......");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
            }
        }

        public void accept(SelectionKey key) {
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector, SelectionKey.OP_READ);
                System.out.println("accept a client : " + sc.socket().getInetAddress().getHostName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start() {
            this.init();
            while (true) {
                try {
                    int events = selector.select();
                    if (events > 0) {
                        Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                        while (selectionKeys.hasNext()) {
                            SelectionKey key = selectionKeys.next();
                            selectionKeys.remove();
                            if (key.isAcceptable()) {
                                accept(key);
                            } else {
                                service.submit(new NioServerHandler(key));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}
