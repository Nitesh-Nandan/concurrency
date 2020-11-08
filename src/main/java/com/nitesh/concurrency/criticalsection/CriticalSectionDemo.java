package com.nitesh.concurrency.criticalsection;

/**
 * This class demonstrate the use of synchronize keyword and how to have single/multiple
 * critical section inside a class
 */
public class CriticalSectionDemo {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventory = new InventoryCounter();
        Thread incThread = new IncrementThread(inventory);
        Thread decThread = new DecrementThread(inventory);

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println("After Finish: " + inventory.getItems());

    }

    /**
     * You can have multiple lock in a class
     * Hence A class have multiple critical section.
     */
    private static class InventoryCounter {
        private int items = 0;
        final Object lock = new Object();

        /**
         * Another way of making a function is synchronized is
         * public void synchronized method1();
         * This is same as wrapping function body inside synchronized(this)
         */
        public void increment() {
            synchronized (lock) {
                this.items++;
            }
        }
        public void decrement() {
            synchronized (lock) {
                this.items--;
            }
        }
        public int getItems() {
            return this.items;
        }
    }

    private static class IncrementThread extends Thread {
        private final InventoryCounter inventory;
        public IncrementThread(InventoryCounter inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run() {
            for(int i=0;i<100000;i++){
                inventory.increment();
            }
        }
    }
    private static class DecrementThread extends Thread {
        private final InventoryCounter inventory;
        public DecrementThread(InventoryCounter inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run() {
            for(int i=0;i<100000;i++){
                inventory.decrement();
            }
        }
    }
}
