package com.kuze.bigdata.study.l11designmodel;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class GuardedSuspension {

    public class Message{
        private String id;
        private String content;

        public Message(String id, String content) {
            this.id = id;
            this.content = content;
        }
    }

    public static class GuardedObject<T> {
        //受保护的对象
        T obj;
        final Lock lock = new ReentrantLock();
        final Condition done = lock.newCondition();
        final int timeout = 2;

        //保存所有GuardedObject
        final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();

        //静态方法创建GuardedObject
        static <K> GuardedObject create(K key) {
            GuardedObject go = new GuardedObject();
            gos.put(key, go);
            return go;
        }

        static <K, T> void fireEvent(K key, T obj) {
            GuardedObject go = gos.remove(key);
            if (go != null) {
                go.onChanged(obj);
            }
        }

        //获取受保护对象
        T get(Predicate<T> p) {
            lock.lock();
            try {
                //MESA管程推荐写法
                while (!p.test(obj)) {
                    done.await(timeout, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
            return obj;
        }

        //事件通知方法
        void onChanged(T obj) {
            lock.lock();
            try {
                this.obj = obj;
                done.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    public void send(Message message){
        //忽略实现
    }

    public Message handleWebReq(String req) {
        String id = UUID.randomUUID().toString();

        Message message = new Message(id, req);

        //创建GuardedObject实例
        GuardedObject<Message> go = GuardedObject.create(id);

        //发送消息
        send(message);

        //等待MQ消息
        Message result = go.get(t -> t != null);

        return  result;
    }

    // 处理完结果的回调
    public void onMessage(Message msg){
        //唤醒等待的线程
        GuardedObject.fireEvent(msg.id, msg);
    }



}
