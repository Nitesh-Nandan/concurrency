package com.nitesh.executor;

import java.util.concurrent.*;

/**
 * In Java, threads are mapped to system-level threads which are operating system's resources.
 * If you create threads uncontrollably, you may run out of these resources quickly.
 *
 * Thread Pool
 * 1. FixedThread Pool - Fixed no of thread, we submit the task in queue (Blocking Queue)
 * 2. CachedThread Pool - Synchronous Queue (Can hold only 1 task)
 * 3. ScheduledThread Pool - Delay Queue
 * 4. SingleThread Pool - A single thread is created to consume the job
 */
public class ThreadPoolIntro {

    /**
     * Creates a fixed size thread pool.
     * Choosing of no of thread is tradeoff.
     * If task are cpu intensive: No of Available processors
     * If task are Non-CPU (Network/IO/DB) intensive: High
     */
    private static class FixedThreadPoolDemo {
        public static void main(String[] args) {
            ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for(int i =0; i<10; i++) {
                service.submit(new Task("Message: " + i));
            }
        }
    }

    /**
     * Synchronous Queue - can hold only 1 task
     * If all threads are busy, then create a new thread for the task and place it in the pool.
     * Life Cycle: If thread is idle for 60 seconds (no task to execute) then kill the thread
     */
    private static class CachedThreadPoolDemo {
        public static void main(String[] args) {
            ExecutorService service = Executors.newCachedThreadPool();
            for(int i =0; i<10; i++) {
                service.submit(new Task("Message: " + i));
            }
        }
    }

    /**
     * Delay Queue:
     * Scheduled the tasks to run based on time delay ( and re-trigger for fixedRate / fixedDelay )
     * LifeCycle: More threads are created if required.
     */
    private static class ScheduledExecutorDemo {
        public static void main(String[] args) {
            ScheduledExecutorService service = new ScheduledThreadPoolExecutor(3);
            // task to run after 10 second delay
            service.schedule(new Task("Scheduled"), 2, TimeUnit.SECONDS);

            // task to run repeatedly every 10 second
            service.scheduleAtFixedRate(new Task("Scheduled At FixedRate"), 5, 10, TimeUnit.SECONDS);

            // task to run repeatedly 10 seconds after previous task completes
            service.scheduleWithFixedDelay(new Task("Scheduled At Fixed Delay"), 10, 3, TimeUnit.SECONDS);
        }
    }

    /**
     * Blocking Queue
     * Task are run in sequential as there is only one thread
     * Recreates thread if killed because of task
     */
    private static class SingleThreadExecutorDemo {
        public static void main(String[] args) {
            ExecutorService service = Executors.newSingleThreadExecutor();
            for(int i =0; i<10; i++) {
                service.submit(new Task("Message: " + i));
            }
        }
    }

    private static class Task implements Runnable {
        private final String message;
        public Task(String msg) {
            this.message = msg;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + message);
        }
    }
}
