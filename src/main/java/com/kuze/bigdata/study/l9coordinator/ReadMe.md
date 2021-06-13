
前面几篇文章我们介绍了线程池、Future、CompletableFuture 和 CompletionService，
仔细观察你会发现这些工具类都是在帮助我们站在任务的视角来解决并发问题，
而不是让我们纠缠在线程之间如何协作的细节上（比如线程之间如何实现等待、通知等）。

1. 对于简单的并行任务，你可以通过“线程池 +Future”的方案来解决
2. 如果任务之间有聚合关系，无论是 AND 聚合还是 OR 聚合，都可以通过 CompletableFuture 来解决
3. 而批量的并行任务，则可以通过 CompletionService 来解决。