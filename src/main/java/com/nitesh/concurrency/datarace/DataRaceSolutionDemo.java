package com.nitesh.concurrency.datarace;

/**
 * These are two ways of resolving DataRace
 *  1. Synchronization of methods which modify shared variables
 *  2. Declaration of shared variable with the volatile keyword.
 */
public class DataRaceSolutionDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(()->{
            for(int i=0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(()->{
            for(int i=0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    /**
     * volatile int sharedVar;
     * public void method() {
     *     // ALL instruction will executed before
     *     read/write (sharedVar)
     *     // All instruction will executed after
     * }
     */
    private static class SharedClass {
        private volatile int x = 0;
        private volatile int y = 0;

        public void  increment() {
            x++;
            y++;
        }
        public void checkForDataRace() {
            if(y>x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}
