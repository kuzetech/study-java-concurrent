package com.kuze.bigdata.study.l6Atomic;

public class AtomicDoc {

    /**
     *
     * 基本数据类型
     * AtomicBoolean
     * AtomicInteger
     * AtomicLong
     *
     * 对象引用类型
     * AtomicReference
     * AtomicStampedReference   解决ABA问题，加版本号
     * AtomicMarkableReference  解决ABA问题，Boolean值区分
     *
     * 数组
     * AtomicIntegerArray
     * AtomicLongArray
     * AtomicReferenceArray
     *
     * 对象属性更新器，反射机制实现，对象属性必须是 volatile 类型，否则报错
     * AtomicIntegerFieldUpdater
     * AtomicLongFieldUpdater
     * AtomicReferenceFieldUpdater
     *
     * 累加器，相对于基本数据类型，仅能用于累加
     * DoubleAccumulator
     * DoubleAdder
     * LongAccumulator
     * LongAdder
     *
     */

}
