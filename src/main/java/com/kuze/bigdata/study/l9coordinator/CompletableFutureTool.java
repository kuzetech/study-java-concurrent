package com.kuze.bigdata.study.l9coordinator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTool {

    public static void makeTea() {
        CompletableFuture<Void> first = CompletableFuture.runAsync(() -> {
            System.out.println("T1:洗水壶...");
            sleep();

            System.out.println("T1:烧开水...");
            sleep();
        });

        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2:洗茶壶...");
            sleep();

            System.out.println("T2:洗茶杯...");
            sleep();

            System.out.println("T2:拿茶叶...");
            sleep();

            return "龙井";
        });

        CompletableFuture<String> result = first.thenCombine(second, (f, tea) -> {
            System.out.println("T1:拿到茶叶:" + tea);
            System.out.println("T1:泡茶...");
            return "上茶:" + tea;
        });

        // result.get() 也可以获取结果, 但是不会抛出运行时的异常
        System.out.println(result.join());
    }

    public static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        makeTea();
    }

    public void showException(){
        CompletableFuture<String> result = CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApply(s -> s + " qq")
                .thenApply(String::toUpperCase)
                .exceptionally(e->{return e.getMessage();});

        System.out.println(result.join());
    }
}
