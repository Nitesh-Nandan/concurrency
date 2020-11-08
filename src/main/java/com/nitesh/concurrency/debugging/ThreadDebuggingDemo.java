package com.nitesh.concurrency.debugging;

public class ThreadDebuggingDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Intentional Exception");
            }
        });

        thread.setName("Misbehaving Thread");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                System.out.println("A critical error happened in thread " + thread.getName()
                + " the error is " + throwable.getMessage());
            }
        });
        thread.start();
    }
}
