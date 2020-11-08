package com.nitesh.concurrency.basic;

class ThreadCreationDemo extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from "
                + Thread.currentThread().getName()); // this.getName();
    }

    public void start() {
        System.out.println("Thread started");
        super.start();
    }
}

public class NewThread {
    public static void main(String[] args) {
        Thread th1 = new ThreadCreationDemo();
        th1.setName("Demo Thread");
        th1.start();
    }
}
