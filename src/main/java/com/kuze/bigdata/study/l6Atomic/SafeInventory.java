package com.kuze.bigdata.study.l6Atomic;

import java.util.concurrent.atomic.AtomicReference;

public class SafeInventory {

    // 库存上限和下限
    class WMRange{
        final int upper;
        final int lower;
        WMRange(int upper,int lower){
            this.upper = upper;
            this.lower = lower;
        }
    }

    final AtomicReference<WMRange> rf = new AtomicReference<>( new WMRange(0,0) );

    public void setUpper(int v){
        while(true){
            WMRange or = rf.get();

            // 检查参数合法性
            if(v < or.lower){
                throw new IllegalArgumentException();
            }

            WMRange nr = new WMRange(v, or.lower);
            if(rf.compareAndSet(or, nr)){
                return;
            }
        }
    }

}
