package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author fhj
 * @version 1.0
 * @ClassName Server
 * @Description TODO
 * @date 2020/5/5  15:46
 **/
public class Server implements Runnable{

    private  Selector selector;

    private  ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private  ByteBuffer writeBuffer =ByteBuffer.allocate(1024);;



    public Server() {


        try {
            //获取多路复用器
            selector = Selector.open();
            //获取通道
            ServerSocketChannel channel = ServerSocketChannel.open();

            //设置非阻塞
            channel.configureBlocking(false);
            //绑定地址
            channel.bind(new InetSocketAddress(8080));

            //注册到复用器上
            channel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("server start=============");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true){
            //开启多路复用器监听
            try {
                selector.select();
                //获取注册的结果集
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    //判断有效
                    if(key.isValid()) {
                        //阻塞状态
                        if (key.isAcceptable()) {
                            registry(key);
                        }
                        //可读状态
                        if (key.isReadable()) {
                            read(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }

    private void registry(SelectionKey key) {

        try {
            //获取ServerSocketChannel
            ServerSocketChannel serverchannel = (ServerSocketChannel)key.channel();
            //获取SocketChannel
            SocketChannel channel = serverchannel.accept();
            //设置非阻塞
            channel.configureBlocking(false);
            //注册到多路复用器
            channel.register(selector,SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void read(SelectionKey key) {
        try {
            //清空缓存区
            this.readBuffer.clear();
            //获取socketChannel
            SocketChannel socketChannel = (SocketChannel) key.channel();
            //读取数据
            int count = socketChannel.read(readBuffer);
            //如果没有数据
            if(count == -1){
                socketChannel.close();
                key.cancel();
                return;
            }
            //数据复位
            this.readBuffer.flip();
            //定义相同大小字节数组
            byte[] bytes = new byte[this.readBuffer.remaining()];
            //读取到bytes
            this.readBuffer.get(bytes);

            String result = new String(bytes).trim();
            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server()).start();
    }
}
