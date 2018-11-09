package com.desaco.architecturepackage.lock;

/**
 * Created by desaco on 2018/11/9.
 */

public class synchronized_java {

    //类锁  静态的获取类锁
    public static void classLock() {
        synchronized (synchronized_java.class) {

        }
    }

    //对象锁
    public void objectLock() {
        synchronized (this) {

        }
    }

    //类和对象如何获得同一个锁
    public void objRequreClassLock() {
        synchronized (synchronized_java.class) {

        }
    }

}
