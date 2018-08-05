package com.concurrent.read.myio;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.FileOutputStream;

/**
 * created on sunyang 2018/7/27 16:33
 * Are you different!"jia you" for me
 */
public class FileReadIoDemo {

    public static void main(String[] args) {
        try {
            System.out.println( "please write:" );
            int count,n = 512;
            byte buffer[] = new byte[n];
            count = System.in.read(buffer);
            File dir = new File("D:/gitspace/concurrent/src/com/concurrent/read/myio/fileOne.txt"); //D:\gitspace\concurrent\src\com\concurrent\read\myio\fileOne.txt
            FileOutputStream os = new FileOutputStream( dir );
            os.write( buffer,0,count );
            os.close();
            System.out.println("File write finish;");
        }catch (Exception e){
            System.out.println("File Write Error!");
        }
    }
}
