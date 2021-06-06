package com.kuze.bigdata.study.l4tool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * 带乐观读的读写锁
 * @param <K>
 * @param <V>
 */
public class StampedLockCache<K, V> {

    private final Map<K, V> cacheMap = new HashMap<>();

    private final StampedLock lock = new StampedLock();

    public void set(K key, V value){
        long timestamp = lock.writeLock();
        try {
            cacheMap.put(key, value);
        }finally {
            lock.unlockWrite(timestamp);
        }
    }

    public V get(K key){
        long timestamp = lock.tryOptimisticRead();
        V value = cacheMap.get(key);
        if(value != null){
            if(!lock.validate(timestamp)){
                timestamp = lock.readLock();  //不支持中断
                //timestamp = lock.readLockInterruptibly();  //如果要支持，使用这个方法
                try{
                    value = cacheMap.get(key);
                }finally {
                    lock.unlockRead(timestamp);
                }
            }
            return value;
        }

        timestamp = lock.writeLock();  //不支持中断
        //timestamp = lock.writeLockInterruptibly();  //如果要支持，使用这个方法
        try{
            value = cacheMap.get(key);
            if(value == null){
                value = searchDB();
            }
            cacheMap.put(key, value);
        }finally {
            lock.unlockWrite(timestamp);
        }

        return value;
    }

    private V searchDB(){
        //发送一个请求搜索数据
        return null;
    }

}
