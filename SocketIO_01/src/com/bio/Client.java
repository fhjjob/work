package com.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author fhj
 * @version 1.0
 * @ClassName Client
 * @Description TODO
 * @date 2020/5/5  11:46
 **/
public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        BufferedWriter writer = null;
        BufferedReader reader = null;
        try {

            socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1",8080));
            System.out.println("start client===================");
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("hello \n");
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(reader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
