package com.kuze.bigdata.study.l4tool;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 多个线程协作等待
 */
public class CyclicBarrierOrderCheck {

    private final List<Object> orderQueue = new Vector<>();
    private final List<Object> paymentQueue = new Vector<>();

    private final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
        Object order = orderQueue.remove(0);
        Object payment = paymentQueue.remove(0);
        check(order, payment);
    });

    public static void check(Object order, Object payment){
        System.out.println("play check");
    }

    public static boolean haveNewOrder(){
        //是否有新的订单
        return true;
    }

    public static Object searchOrder(){
        //发送一个请求搜索今日订单
        return null;
    }

    public static Object searchPayment(){
        //发送一个请求搜索今日结账
        return null;
    }

    public void init() throws InterruptedException {
        Thread orderCheck = new Thread(()->{
            while(haveNewOrder()){
                Object order = searchOrder();
                orderQueue.add(order);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        orderCheck.start();

        Thread paymentCheck = new Thread(()->{
            while(haveNewOrder()){
                Object payment = searchPayment();
                paymentQueue.add(payment);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        paymentCheck.start();

        Thread.sleep(5000);
    }




}
