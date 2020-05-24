package com.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author fhj
 * @version 1.0
 * @ClassName ServerCompletionHander
 * @Description TODO
 * @date 2020/5/5  16:54
 **/
public class ServerCompletionHander implements CompletionHandler<AsynchronousSocketChannel,Server> {
    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        attachment.sc.accept(attachment,this);
        read(result);
    }

    private void read(AsynchronousSocketChannel asc) {
        //读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                String str = new String(attachment.array()).trim();
                System.out.println("server 接收到数据size："+result);
                System.out.println("server 接收到数据："+str);
                String response = "server 响应数据:"+str;
                write(asc,response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String response) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(response.getBytes());
        byteBuffer.flip();
        asc.write(byteBuffer);
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }
}
