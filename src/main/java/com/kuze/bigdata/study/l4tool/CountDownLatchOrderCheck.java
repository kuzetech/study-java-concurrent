package com.kuze.bigdata.study.l4tool;

import java.util.concurrent.*;

/**
 * 多个线程协作等待
 */
public class CountDownLatchOrderCheck {

    public static Object searchOrder(){
        //发送一个请求搜索今日订单
        return null;
    }

    public static Object searchPayment(){
        //发送一个请求搜索今日结账
        return null;
    }

    public static boolean haveNewOrder(){
        //是否有新的订单
        return true;
    }

    public static void check(Object order, Object payment){
        //对账
    }

    private static Object orderResult;
    private static Object paymentResult;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        while(haveNewOrder()){

            CountDownLatch latch = new CountDownLatch(2);

            executorService.execute(()->{
                orderResult = searchOrder();
                latch.countDown();
            });

            executorService.execute(()->{
                paymentResult = searchPayment();
                latch.countDown();
            });

            latch.await();
            //执行订单和付款单对账
            check(orderResult, paymentResult);

            //5秒之后重新检查
            Thread.sleep(5000);
        }
    }

}
