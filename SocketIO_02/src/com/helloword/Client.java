package com.helloword;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author fhj
 * @version 1.0
 * @ClassName Client
 * @Description TODO
 * @date 2020/5/25  21:16
 **/
public class Client {

    public static void main(String[] args) throws InterruptedException {


        EventLoopGroup work = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(work).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHander());
                    }
                });



        ChannelFuture future = b.connect("127.0.0.1",8081).sync();

        future.channel().writeAndFlush(Unpooled.copiedBuffer("hello".getBytes()));
        Thread.sleep(5000);
        future.channel().closeFuture().sync();
        work.shutdownGracefully();

    }
}
