package com.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author fhj
 * @version 1.0
 * @ClassName Server
 * @Description TODO
 * @date 2020/5/5  11:46
 **/
public class Server {

     final static int  PORT= 8080;

    public static void main(String[] args) {
        ServerSocket  server = null;
        try {
            server = new ServerSocket(PORT);
            System.out.println("start server ===================");
            Socket socket = server.accept();
            new Thread(new ServerHander(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
