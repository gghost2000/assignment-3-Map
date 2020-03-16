package com.study.assignment3.Map;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class MapService {

    public final static int THREAD_POOL_SIZE = 20;


    public long performTime(final Map<String, Integer> testMap) throws InterruptedException {
        long startTime = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 500000; i++) {
                        Integer randomNum = (int) (Math.random() * 550000);
                        Integer getValue = testMap.get(String.valueOf(randomNum));
                        testMap.put(String.valueOf(randomNum), randomNum);
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000L;
    }


}
