package com.nitesh.concurrency.datasharing;

public class SharedVariableProblemDemo {
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

    private static class InventoryCounter {
        private int items = 0;
        public void increment() {
            this.items++;
        }
        public void decrement() {
            this.items--;
        }
        public int getItems() {
            return this.items;
        }
    }
}
