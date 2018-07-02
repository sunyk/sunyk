package com.pattern.prototype.deep;

import java.io.*;
import java.util.Date;
import java.util.Formatter;

/**
 * Create by sunyang on 2018/6/26 0:18
 * For me:One handred lines of code every day,make myself stronger.
 */
public class QiTianDaSheng extends Monkey implements Cloneable,Serializable {


    public JinGuBang jinGuBang;

    public QiTianDaSheng() {
        this.birthday = new Date();
        this.jinGuBang = new JinGuBang();
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return deepClone();
    }

    public Object deepClone(){
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            QiTianDaSheng copy = (QiTianDaSheng) ois.readObject();
            copy.birthday = new Date();

            return copy;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (bos != null){
                    bos.close();
                }
                if (oos != null){
                    oos.close();
                }
                if (bis != null){
                    bis.close();
                }
                if (ois != null){
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
