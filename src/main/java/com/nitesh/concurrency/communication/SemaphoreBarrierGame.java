package com.nitesh.concurrency.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Can be used to restrict the number of "users" to a particular resource or a group of resource
 *
 * Semaphore doesn't have a notion of owner thread
 * Many thread can acquire a permit
 * The same thread can acquire the semaphore multiple times
 * Difference between locks and semaphores
 *  1. Any thread can release a semaphore (no ownership)
 * Inter-thread communication for producer consumer, using semaphore
 * The same thread can acquire the semaphore multiple times
 * The binary semaphore (initialized with 1) is not reentrant
 */
public class SemaphoreBarrierGame {
    public static void main(String [] args){
        int numberOfThreads = 200; //or any number you'd like

        List<Thread> threads = new ArrayList<>();

        Barrier barrier = new Barrier(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
        }

        for(Thread thread: threads) {
            thread.start();
        }
    }

    public static class Barrier {
        private final int numberOfWorkers;
        private final Semaphore semaphore = new Semaphore(0);
        private int counter = 0;
        private final Lock lock = new ReentrantLock();

        public Barrier(int numberOfWorkers) {
            this.numberOfWorkers = numberOfWorkers;
        }

        public void barrier() {
            lock.lock();
            boolean isLastWorker = false;
            try {
                counter++;

                if (counter == numberOfWorkers) {
                    isLastWorker = true;
                }
            } finally {
                lock.unlock();
            }

            if (isLastWorker) {
                semaphore.release(numberOfWorkers-1);
            } else {
                try {
                    semaphore.acquire();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public static class CoordinatedWorkRunner implements Runnable {
        private final Barrier barrier;

        public CoordinatedWorkRunner(Barrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                task();
            } catch (InterruptedException ignored) {
            }
        }

        private void task() throws InterruptedException {
            // Performing Part 1
            System.out.println(Thread.currentThread().getName()
                    + " part 1 of the work is finished");

            barrier.barrier();

            // Performing Part2
            System.out.println(Thread.currentThread().getName()
                    + " part 2 of the work is finished");
        }
    }
}
