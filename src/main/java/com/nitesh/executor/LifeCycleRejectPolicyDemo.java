package com.nitesh.executor;

import java.util.concurrent.*;

/**
 * Different types of Rejection Policy
 * 1. AbortPolicy - Submitting new tasks throws RejectExecutionException (Runtime Exception)
 * 2. DiscardPolicy - Submitting new tasks silently discards it.
 * 3. DiscardOldestPolicy - Submitting new tasks drops existing oldest task, and new task is added
 *                          to the queue.
 * 4. CallerRunsPolicy - Submitting new tasks will execute the task on the caller thread itself.
 *                      This can create feedback loop where caller thread is busy executing the task and
 *                      can't submit new tasks as fast place.
 *
 */
public class LifeCycleRejectPolicyDemo {

    /**
     * service.shutdown() - initiate shutdown
     * service.isShutdown() - will return true, since shutdown has begun
     * service.isTerminated() - will return if all tasks are completed including queues ones.
     * service.awaitTermination(timeout, timeunit) - block until all tasks are completed or if timeout occurs.
     * List<Runnable> runnable = service.shutdownNow(); will initiate shutdown and return all queued tasks.
     */
    private static class AbortPolicyDemo {
        public static void main(String[] args) {
            ExecutorService service = new ThreadPoolExecutor(
                    10, 100, 120,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(300)
            );
            try {
                service.execute(new Task());
            }catch (RejectedExecutionException e) {
                System.out.println("Task Rejected " + e.getMessage());
            }
        }
    }

    private static class Task implements Runnable {
        private String msg;
        @Override
        public void run() {
            System.out.println("Message: " + this.msg);
        }
    }

}
