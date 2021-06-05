package com.kuze.bigdata.study.l1base;

import java.util.ArrayList;
import java.util.List;

public class WaitAndNotify {

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
