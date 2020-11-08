package com.nitesh.concurrency.datarace;

/**
 * Compiler and CPU may execute the instructions Out of Order to optimize performance and Utilization.
 * They will do so while maintaining the logical correctness of the code.
 * Out Of Order execution by the compiler and CPU are important features to speed up the code.
 * The compiler re-arranges instructions for better
 *  - Branch predication (optimized loops, "if" statement etc.)
 *  - Vectorization - parallel instruction execution (SIMD)
 *  - Prefetching instructions - better cache performance
 * CPU re-arranges instructions for better hardware units utilization.
 */
public class DataRaceDemo {

    public static void main(String[] args) {
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

    private static class SharedClass {
        private int x = 0;
        private int y = 0;

        public void increment() {
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
