package com.kuze.bigdata.study.l1base;

import java.util.ArrayList;
import java.util.List;


public class WaitAndNotify {

    /**
     * 转账时需要同时拿到两个账户
     */
    public static class Allocator{
        private List<Object> list = new ArrayList<>();

        public synchronized void apply(Object sourceAccount, Object targetAccount){
            while(list.contains(sourceAccount) || list.contains(targetAccount)){
                try {
                    //release lock and wait
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(sourceAccount);
            list.add(targetAccount);
        }

        public synchronized void free(){
            list.clear();
            notifyAll();
        }
    }
}
