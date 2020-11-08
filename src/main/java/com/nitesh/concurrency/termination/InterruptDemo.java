package com.nitesh.concurrency.termination;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(50000);
            }
            catch (InterruptedException ex) {
                System.out.println("Existing Blocking thread");
            }
        }
    }
}
