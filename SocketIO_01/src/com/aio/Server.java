package com.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fhj
 * @version 1.0
 * @ClassName Server
 * @Description TODO
 * @date 2020/5/5  16:53
 **/
public class Server {

    //线程池
    private ExecutorService executor;

    //异步渠道组
    private AsynchronousChannelGroup channelGroup;

    //异步渠道
    public AsynchronousServerSocketChannel sc;


    public Server() {
        try {
            executor = Executors.newCachedThreadPool();
            channelGroup = AsynchronousChannelGroup.withCachedThreadPool(executor,1);
            sc = AsynchronousServerSocketChannel.open(channelGroup);
            sc.bind(new InetSocketAddress(8080));
            sc.accept(this,new ServerCompletionHander());
            System.out.println("server start ================");
            Thread.sleep(Integer.MAX_VALUE);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
