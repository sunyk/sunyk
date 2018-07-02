package com.pattern.prototype.deep;

/**
 * Create by sunyang on 2018/7/3 0:19
 * For me:One handred lines of code every day,make myself stronger.
 */

import java.io.*;
import java.util.Date;

/**
 * 齐天大圣
 */
public class TheGreatestSage extends Monkey implements Cloneable,Serializable{

    //金箍棒
   private GoldRingedStaff staff;

    //从石头缝里蹦出来
    public TheGreatestSage(){
        this.staff = new GoldRingedStaff();
        this.birthday = new Date();
        this.height = 150;
        this.width = 70;
        System.out.println("------克隆是不走构造方法的，直接是字节流复制-----------------------");
    }

    //分身技能
//    @Override-浅克隆
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
    protected Object clone() throws CloneNotSupportedException {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            //序列化
             bos = new ByteArrayOutputStream();
             oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            //反序列化
             bis = new ByteArrayInputStream(bos.toByteArray());
             ois = new ObjectInputStream(bis);
            TheGreatestSage copy = (TheGreatestSage) ois.readObject();
            copy.setBirthday(new Date());
            return copy;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                bos.close();
                oos.close();
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    //变化
    public void change() throws CloneNotSupportedException {
        TheGreatestSage copySage = (TheGreatestSage) clone();
        System.out.println("大圣本尊生日是：" + this.getBirthday().getTime());
        System.out.println("六耳猕猴的生日是:" + copySage.getBirthday().getTime());
        System.out.println("大圣本尊和六耳猕猴是否为同一个对象：" + (this == copySage));
        System.out.println("大圣本尊持有金箍棒和六耳猕猴持有金箍棒是否为同一个对象" + (this.getStaff() == copySage.getStaff()));
    }

    public GoldRingedStaff getStaff() {
        return staff;
    }

    public void setStaff(GoldRingedStaff staff) {
        this.staff = staff;
    }
}
