package com.study.assignment3.Map;

import org.springframework.stereotype.Service;
import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.map.LazyMap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class MapService {

    public final static int THREAD_POOL_SIZE = 20;


    public long performTime(final Map<String, Integer> testMap) throws InterruptedException {
        long startTime = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executorService.execute(
                    new Thread(() -> {
                        for (int time = 0; time < 500000; time++) {
                            Integer randomNum = (int) (Math.random() * 500000);
                            Integer gatValue = testMap.get(String.valueOf(randomNum));
                            testMap.put(String.valueOf(randomNum),randomNum);
                        }
                    })
            );
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000L;
    }

    public Map getLazyMap() {
        Factory<Date> factory = new Factory<Date>() {
            public Date create() {
                return new Date();
            }
        };
        Map<String, Date> lazyMap = LazyMap.lazyMap(new HashMap<String, Date>(), factory);
        return lazyMap;
    }


}
