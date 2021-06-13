package com.kuze.bigdata.study.l11designmodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalPool {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(3);

        ThreadLocal<DateFormat> tl = new ThreadLocal<>();

        es.execute(()->{
            //ThreadLocal增加变量
            tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            try {
                // 省略业务逻辑代码
            }finally {
                //手动清理ThreadLocal
                tl.remove();
            }
        });

    }

}
