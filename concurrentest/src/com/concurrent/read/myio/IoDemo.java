package com.concurrent.read.myio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * created on sunyang 2018/7/27 13:39
 * Are you different!"jia you" for me
 */
public class IoDemo {

    public static void main(String[] args) throws IOException {
        /*标准输入流
        int b;
        try {
            System.out.print("please Input:");
            while ((b = System.in.read()) != -1){
                System.out.print((char) b);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }*/

        String s;
        //创建缓冲区阅读器从键盘逐行读入数据
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(ir);
        System.out.println("windows系统：Ctrl-z 退出");
        try {
            //读一行数据，并标准输出至显示器
            s = in.readLine();
            //readline（）方法运行时若发生I/0错误，抛出IO异常
            while (s != null){
                System.out.println("Read:" + s);
                s = in.readLine();
            }

        }catch (Exception e){

        }finally {
            in.close();
        }

    }
}
