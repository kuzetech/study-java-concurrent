package com.kuze.bigdata.study.l10forkjoin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MapReduce {

    public static void main(String[] args) {

        String[] fc = {"hello world",
                "hello me",
                "hello fork",
                "hello join",
                "fork join in world"};

        //创建ForkJoin线程池
        ForkJoinPool fjp =
                new ForkJoinPool(3);

        // 创建任务
        MR mr = new MR(fc, 0, fc.length);

        // 开始任务，并阻塞当前进程直到任务完成
        Map<String, Long> result = fjp.invoke(mr);

        //输出结果
        result.forEach((k, v)->
                System.out.println(k+":"+v));
    }

    static class MR extends RecursiveTask<Map<String, Long>> {

        private String[] fc;
        private int start, end;

        //构造函数
        MR(String[] fc, int fr, int to){
            this.fc = fc;
            this.start = fr;
            this.end = to;
        }

        @Override
        protected Map<String, Long> compute() {
            if(end - start == 1){
                return splitWord(fc[0]);
            }else{
                // 拆分任务
                int mid = (end - start)/2;
                MR mr1 = new MR(fc,start,mid);
                MR mr2 = new MR(fc,mid,end);

                Map<String, Long> compute = mr1.compute();
                Map<String, Long> forkResult = mr2.fork().join();

                return merge(compute, forkResult);
            }
        }

        //合并结果
        private Map<String, Long> merge(Map<String, Long> r1, Map<String, Long> r2) {
            Map<String, Long> result = new HashMap<>();
            result.putAll(r1);
            //合并结果
            r2.forEach((k, v) -> {
                Long c = result.get(k);
                if (c != null)
                    result.put(k, c+v);
                else
                    result.put(k, v);
            });
            return result;
        }

        //统计单词数量
        private Map<String, Long> splitWord(String line) {
            Map<String, Long> result =
                    new HashMap<>();
            //分割单词
            String [] words =
                    line.split("\\s+");
            //统计单词数量
            for (String w : words) {
                Long v = result.get(w);
                if (v != null)
                    result.put(w, v+1);
                else
                    result.put(w, 1L);
            }
            return result;
        }
    }

}
