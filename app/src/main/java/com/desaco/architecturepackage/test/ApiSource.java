package com.desaco.architecturepackage.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by desaco on 2018/11/8.
 */

public class ApiSource {

    private void test2(){

    }

    private void  test(){
        ThreadPoolExecutor pool;
    }

    private static Runnable getThread(final int i) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        };
    }

    public static void main(String args[]) {
        ExecutorService fixPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            fixPool.execute(getThread(i));
        }
        fixPool.shutdown();
    }
//    ThreadLocalMap map;

    class ThreadDemo implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int sum = 0;

            for (int i = 0; i <= 100000; i++) {
                sum += i;
            }

            return sum;
        }
    }

}
