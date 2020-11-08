package com.nitesh.concurrency.termination;

public class DaemonThreadJoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new BackGroundThread();
        thread.setDaemon(true);
        thread.start();
        thread.join(2000);
        System.out.println("Finished");
    }

    private static class BackGroundThread extends Thread {
        @Override
        public void run () {
            while (true) {
                System.out.println("Hello");
            }
        }
    }
}
