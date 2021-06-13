package com.kuze.bigdata.study.l11designmodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ThreadLocalDataFormat {

    static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    );

    static DateFormat get() {
        return tl.get();
    }

    public static void main(String[] args) {
        // 不同线程执行下面代码,返回的df是不同的
        DateFormat df = ThreadLocalDataFormat.get();
    }
}
