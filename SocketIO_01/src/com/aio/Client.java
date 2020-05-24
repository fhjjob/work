package com.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * @author fhj
 * @version 1.0
 * @ClassName Client
 * @Description TODO
 * @date 2020/5/5  16:53
 **/
public class Client  implements Runnable{

    private AsynchronousSocketChannel asc;

    public Client() {

        try {
            asc = AsynchronousSocketChannel.open();
            asc.connect(new InetSocketAddress("127.0.0.1",8080));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String request){
        try {
            asc.write(ByteBuffer.wrap(request.getBytes())).get();
            read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void read() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            asc.read(buffer).get();
            buffer.flip();
            System.out.println(new String(buffer.array(),"utf-8").trim());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true){}
    }


    public static void main(String[] args) throws InterruptedException {
        Client c1 = new Client();
        Client c2 = new Client();
        Client c3 = new Client();

        new Thread(c1,"c1").start();
        new Thread(c2,"c2").start();
        new Thread(c3,"c3").start();

        Thread.sleep(1000);

        c1.write("c1 aa");
        c2.write("c2 bbb");
        c3.write("c3 cccc");

    }
}
