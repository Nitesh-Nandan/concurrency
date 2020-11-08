package com.nitesh.concurrency.basic;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are now in thread: "
                        + Thread.currentThread().getName());
                System.out.println("Current thread priority is: "
                        + Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY); // static priority

        System.out.println("We are in Thread: " + Thread.currentThread().getName()
                + " before starting a new Thread");
        thread.start();
        System.out.println("We are in Thread: " + Thread.currentThread().getName()
                + " after starting a new Thread");

        Thread.sleep(5000);

        System.out.println("Just Finished");
    }
}
