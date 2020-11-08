package com.nitesh.executor;

import java.util.Random;
import java.util.concurrent.*;

/**
 * future.cancel() - Cancel the task
 * future.isCancelled() - Return true if task was cancelled.
 * future.isDone - Returns true is task is completed (successfully or otherwise)
 */
public class CallableFutureDemo {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> future = service.submit(new Task());
        try {
            // future.get(timeout, timeunit) - overloaded method
            Integer result = future.get(); // blocking operation
            System.out.println("Result is " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
