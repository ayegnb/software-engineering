package com.company;

import java.util.concurrent.*;
import static org.apache.commons.math3.primes.Primes.isPrime;

class MyCallable implements Callable<Boolean[]> {
    private int start;
    private int end;
    private CountDownLatch latch;

    public MyCallable(int start, int end, CountDownLatch latch) {
        super();
        this.start = start;
        this.end = end;
        this.latch = latch;
    }

    @Override
    public Boolean[] call() throws Exception {
        Boolean[] result = new Boolean[end - start + 1];
        try {
            for(int i = 0; i < result.length; i++) {
                if(isPrime(start+i)) {
                    result[i] = true;
                }
            }
            latch.countDown();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}

public class Main {
    public static Long calculatePerformance(int numOfThreads, int upperBound) throws InterruptedException {
        Long startTime = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
        CountDownLatch latch = new CountDownLatch(numOfThreads);
        for (int i = 1; i < numOfThreads + 1; i++) {
            Future future = service.submit(new MyCallable(upperBound/numOfThreads * (i - 1) + 1, upperBound/numOfThreads * i, latch));
        }
        latch.await();
        Long endTime = System.nanoTime();
        Long totalTime = (endTime-startTime)/1000000;
        service.shutdown();
        return totalTime;
    }

    public static void main(String args[]) throws InterruptedException {
        System.out.println("Single thread performance: " + calculatePerformance(1, 10000000) + " milliseconds\n" +
                "Multiple threads performance: " + calculatePerformance(4, 10000000) + " milliseconds");
    }
}
