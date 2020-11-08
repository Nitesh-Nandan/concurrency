package com.nitesh.concurrency.termination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 232L, 4656L, 23L, 51246L);
        List<FactorialThread> threads = new ArrayList<>();
        for(long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for(Thread thread : threads) {
            thread.start();
            thread.join(2000);
        }

        for(int i=0;i<inputNumbers.size();i++) {
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.isFinished()) {
                System.out.println("Factorial of "
                        + inputNumbers.get(i) + " is " + factorialThread.getResult());
            }
            else {
                System.out.println("The calculation of "
                        + inputNumbers.get(i) + " is in progress");
            }
        }
        System.out.println("Finished");
    }

    private static class FactorialThread extends Thread {
        private final long inputNumber;
        private BigInteger result = BigInteger.ONE;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.setName(Long.toString(inputNumber));
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tmpResult = BigInteger.ONE;
            for(long i=n; i>0; i--) {
                tmpResult = tmpResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tmpResult;
        }

        public boolean isFinished() {
            return this.isFinished;
        }

        public BigInteger getResult() {
            return this.result;
        }
    }
}
