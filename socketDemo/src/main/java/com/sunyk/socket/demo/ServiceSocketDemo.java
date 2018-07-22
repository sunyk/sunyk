package com.sunyk.socket.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create by sunyang on 2018/6/18 0:20
 * For me:One handred lines of code every day,make myself stronger.
 */
public class ServiceSocketDemo {

    public static void main(String[] args) throws IOException{
        ServerSocket socket = null;
        BufferedReader reader = null;

        try {
            socket = new ServerSocket(8090);
            Socket s = socket.accept();//等待客户端连接
            //获得输入流
            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(reader.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                reader.close();
            }
            if (socket != null){
                socket.close();
            }

        }



    }

}
