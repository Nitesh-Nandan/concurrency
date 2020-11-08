package com.nitesh.concurrency.locking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Fair lock - Equal chance for all threads - slower
 * Unfair Lock - Faster (more throughput) Possible thread starvation
 * Extra Methods in ReentrantLock
 *  1. getHoldCount - This method returns the count of the number of locks held on the resource.
 *  2. isHeldByCurrentThread - This method returns true if the lock on the resource
 *                             is held by the current thread.
 *  3. tryLock() - return true in successful acquisition of lock (Doesn't honor the fairness).
 *  4. tryLock(long timeout, TimeUnit unit) - As per the method,
 *                                          the thread waits for a certain time.
 *  5. lockInterruptibly() -  It means that if the current thread is waiting for lock
 *                            but some other thread requests the lock, then the current thread
 *                            will be interrupted and return immediately without acquiring lock.
 *
 */
public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedResource sharedResource = new SharedResource();
        Thread writer = new Thread(() -> {
            for(int i=0;i<10000;i++) {
                sharedResource.update();
            }
            System.out.println("Writer Finished");
        });
        Thread reader = new Thread(() -> {
            for(int i=0;i<10000;i++) {
                sharedResource.read();
            }
            System.out.println("Reader Finished");
        });
        reader.start();
        writer.start();
        reader.join();
        writer.join();
    }

    private static class SharedResource {
        private final ReentrantLock lock =  new ReentrantLock(true);
        private final Random random = new Random();
        private final List<Integer> list = new ArrayList<>();

        public SharedResource() {
            for(int i=0;i<10;i++) {
                list.add(random.nextInt(10000));
            }
        }

        public void update() {
            lock.lock();
            try {
                System.out.println("Lock Acquired by writer");
                list.add(random.nextInt(10), random.nextInt(10000));
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void read() {
            lock.lock();
            try {
                for(Integer num : list) {
                    System.out.print(num + " ");
                }
                System.out.println();
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            finally {
                lock.unlock();
            }
        }
    }
}
