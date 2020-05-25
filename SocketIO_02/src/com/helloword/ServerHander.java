package com.helloword;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author fhj
 * @version 1.0
 * @ClassName ServerHander
 * @Description TODO
 * @date 2020/5/25  21:06
 **/
public class ServerHander extends ChannelHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel active... ");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf bf = (ByteBuf) msg;
        byte[] bt = new byte[bf.readableBytes()];
        bf.readBytes(bt);
        System.out.println("client: "+new String(bt,"utf-8"));

        ctx.writeAndFlush(Unpooled.copiedBuffer("return server  ok".getBytes()));
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读完了");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常关闭");
        ctx.close();
    }
}
