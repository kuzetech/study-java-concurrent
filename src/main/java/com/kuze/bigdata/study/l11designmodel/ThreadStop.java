package com.kuze.bigdata.study.l11designmodel;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadStop extends Thread{

    private AtomicBoolean terminated = new AtomicBoolean(false);

    private boolean started = false;

    @Override
    public void run() {
        if(started){
            return;
        }
        started = true;
        while(!terminated.get()){
            // 执行业务逻辑
            try {
                System.out.println("我准备睡3秒");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // catch之后重制线程状态，需要重新设置
                System.out.println("被打断了");
                Thread.currentThread().interrupt();
            }
        }

        started = false;
    }

    public void setTerminated(){
        terminated.set(true);
        this.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadStop stop = new ThreadStop();
        stop.start();
        Thread.sleep(1000);
        stop.setTerminated();
    }
}
