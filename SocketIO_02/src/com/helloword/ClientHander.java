package com.helloword;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author fhj
 * @version 1.0
 * @ClassName ClientHander
 * @Description TODO
 * @date 2020/5/25  21:20
 **/
public class ClientHander extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf bf = (ByteBuf) msg;
            byte[] b = new byte[bf.readableBytes()];
            bf.readBytes(b);
            System.out.println(new String(b, "utf-8"));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exception");
        ctx.close();
    }
}
