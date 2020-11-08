package com.nitesh.concurrency.atomic;

import java.util.Random;

/**
 * All the getter and Setters are atomic
 * All assignment to primitive types are safe except long and double
 * Atomic: int, short, byte, float, char, boolean
 * Assign volatile to long and double to make it atomic
 */
public class AtomicDemo {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();
        Thread th1 = new BusinessLogic(metrics);
        Thread th2 = new BusinessLogic(metrics);
        Thread th3 = new MetricPrinter(metrics);

        th1.start();
        th2.start();
        th3.start();
    }

    private static class MetricPrinter extends Thread {
        private final Metrics metrics;
        public MetricPrinter(Metrics metrics) {
            this.metrics = metrics;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                    System.out.println("Average is: " + metrics.getAverage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class BusinessLogic extends Thread {
        private final Metrics metrics;
        private final Random random;
        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
            random = new Random();
        }
        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                metrics.addSample(end-start);
            }
        }
    }

    private static class Metrics {
        private long count = 0;

        /** By default double are non-atomic, keyword volatile make sure operation to be atomic */
        private volatile double average = 0.0;

        /** This function used by multiple thread to avoid race condition uses synchronized keyword*/
        public synchronized void addSample(long sample) {
            double currSum = average*count;
            count++;
            average = (currSum + sample) / count;
        }

        /** This operation will be atomic as average has volatile */
        public double getAverage() {
            return average;
        }
    }
}
