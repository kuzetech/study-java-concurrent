package com.kuze.bigdata.study.l4tool;

import javax.print.attribute.standard.Finishings;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 非常严格的读写锁
 * @param <K>
 * @param <V>
 */
public class ReadWriteLockCache<K, V> {

    private final Map<K, V> cacheMap = new HashMap<>();

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock writeLock =  rwl.writeLock();
    private final Lock readLock =  rwl.readLock();

    public void set(K key, V value){
        writeLock.lock();
        try{
            cacheMap.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }

    public V get(K key){
        V value = null;
        readLock.lock();
        try{
            value = cacheMap.get(key);
        }finally {
            readLock.unlock();
        }

        if(value != null){
            return value;
        }

        writeLock.lock();
        try{
            //再次查询
            value = cacheMap.get(key);
            if(value == null){
                value = searchDB();
            }
            cacheMap.put(key, value);
        }finally {
            writeLock.unlock();
        }

        return value;
    }

    private V searchDB(){
        //发送一个请求搜索数据
        return null;
    }

}
