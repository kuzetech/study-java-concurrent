package com.kuze.bigdata.study.l5collection;

public class CollectionDoc {

    /**
     *
     * 同步容器
     * Vector
     * Stack
     * HashTable
     *
     * 同步容器由 synchronized 实现，性能不好
     * 并且如果要遍历容器需要先锁定
     * synchronized (list) {
     *    Iterator i = list.iterator();
     *    while (i.hasNext()){
     *        foo(i.next());
     *    }
     * }
     *
     * 并发容器
     * List
     * CopyOnWriteArrayList
     *
     * Map
     * ConcurrentHashMap
     * ConcurrentSkipListMap 有序key
     *
     * Set
     * CopyOnWriteArraySet
     * ConcurrentSkipListSet
     *
     * BlockingQueue
     * ArrayBlockingQueue   有界
     * LinkedBlockingQueue  有界
     * SynchronousQueue
     * LinkedTransferQueue 结合 LinkedBlockingQueue 和 SynchronousQueue 功能
     * PriorityBlockingQueue 支持优先级出队
     * DelayQueue 支持延迟出队
     *
     * BlockingDeque
     * LinkedBlockingDeque
     *
     * NoBlockingQueue
     * ConcurrentLinkedQueue
     *
     * NoBlockingDeque
     * ConcurrentLinkedDeque
     *
     *
     */

}
