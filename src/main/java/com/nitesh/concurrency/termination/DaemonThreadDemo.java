package com.nitesh.concurrency.termination;

import java.math.BigInteger;

public class DaemonThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable target;
        Thread th1 = new Thread(new LongComputationTask(new BigInteger("2000"), new BigInteger("100000")));
        th1.setDaemon(true);
        th1.start();
        Thread.sleep(1000);
        th1.interrupt();
    }
    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + "= " + pow(base,power));
        }

        private BigInteger pow (BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for(BigInteger i = BigInteger.ZERO;i.compareTo(power)!=0;i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }
    }
}
