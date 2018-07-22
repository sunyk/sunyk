package com.sunyk.socket.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Create by sunyang on 2018/6/18 0:28
 * For me:One handred lines of code every day,make myself stronger.
 */
public class ClientSocketDemo {


    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8090);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello");

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (socket != null){
                socket.close();
            }
        }
    }

}
