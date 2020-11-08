package com.nitesh.concurrency.locking;

import java.util.Random;

/**
 * Condition for DeadLock:
 *  1. Mutual Exclusion: Only ohe thread can have exclusive access to a resource.
 *  2. Hold and Wait: At least one thread is holding a resource and is waiting for another resource.
 *  3. Non-preemptive allocation: A resource is released only after the thread is done using it.
 *  4. Circular Wait: A chain of at least two threads each one is holding one resource
 *                    and waiting for another resource.
 * All the condition should be satisfy to have deadlock.
 *
 * Solution:
 *  1. Avoid Circular wait: Enforce a strict order in lock acquisition.
 *  2. Other Techniques:
 *     1. Deadlock Detection:- WatchDog
 *     2. Thread interruption (not possible with synchronized)
 *     3. tryLock operations (not possible with synchronized)
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainA = new Thread(new TrainA(intersection));
        Thread trainB = new Thread(new TrainB(intersection));

        trainA.start();
        trainB.start();
    }

    private static class TrainA implements Runnable {
        private final Intersection intersection;
        private final Random random = new Random();
        public TrainA(Intersection intersection){
            this.intersection = intersection;
        }
        @Override
        public void run() {
            while (true) {
                long sleeepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleeepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoadA();
            }
        }
    }
    private static class TrainB implements Runnable {
        private final Intersection intersection;
        private final Random random = new Random();
        public TrainB(Intersection intersection){
            this.intersection = intersection;
        }
        @Override
        public void run() {
            while (true) {
                long sleeepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleeepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoadB();
            }
        }
    }

    /**
     * Locking Strategies
     *  1. Coarse-grained Locking: Single Lock - not preferable for optimization
     *  2. Fine-grained Locking: Better parallelism but introduce the problem of deadlock.
     */
    private static class Intersection {
        private final Object roadA = new Object();
        private final Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is Passing through Road A");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void takeRoadB() {
            synchronized (roadB) {
                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());
                synchronized (roadA) {
                    System.out.println("Train is Passing through Road B");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
