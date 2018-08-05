package com.concurrent.read.myio;

import java.io.*;

/**
 * created on sunyang 2018/7/27 14:55
 * Are you different!"jia you" for me
 */
public class FileIoDemo {

    public static void main(String[] args) {
        File dir = new File("D:/gitspace/concurrent/src/com/concurrent/read/myio/fileOne.txt"); //D:\gitspace\concurrent\src\com\concurrent\read\myio\fileOne.txt
//        File f1 = new File(dir, "fileOne.txt");
        String[] context;
        FileInputStream fi = null;
        FileReader fr = null;
        if (dir.exists()){
            try {
                 fi = new FileInputStream(dir);
//                fr = new FileReader(String.valueOf(fi));
                System.out.println(fi.read());
                int n=512;   byte  buffer[]=new  byte[n];
                while((fi.read(buffer,0,n)!=-1)&&(n>0)){
                    System.out.println(new String(buffer) );
                }

//                FileOutputStream fo = new FileOutputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    fi.close();
//                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
