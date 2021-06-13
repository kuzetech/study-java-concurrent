package com.kuze.bigdata.study.l11designmodel;

public class ThreadPoolStop {

    /**
     * shutdown() 方法是一种很保守的关闭线程池的方法。
     * 线程池执行 shutdown() 后，就会拒绝接收新的任务，
     * 但是会等待线程池中正在执行的任务和已经进入阻塞队列的任务都执行完之后才最终关闭线程池。
     *
     * 而 shutdownNow() 方法，相对就激进一些了，
     * 线程池执行 shutdownNow() 后，会拒绝接收新的任务，
     * 同时还会中断线程池中正在执行的任务，已经进入阻塞队列的任务也被剥夺了执行的机会，
     * 不过这些被剥夺执行机会的任务会作为 shutdownNow() 方法的返回值返回。
     * 因为 shutdownNow() 方法会中断正在执行的线程，所以提交到线程池的任务，如果需要优雅地结束，就需要正确地处理线程中断。
     *
     */

}
