package com.study.assignment3.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;


@ExtendWith(MockitoExtension.class)
public class MapServiceTest {

    @InjectMocks
    MapService mapService;

    @Test
    public void performTest() throws InterruptedException {
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<String, Integer>());
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<String, Integer>();
        long synchronizedMapTime =  mapService.performTime(synchronizedMap);
        long concurrentMapTime = mapService.performTime(concurrentMap);
        Assert.isTrue(concurrentMapTime< synchronizedMapTime,"cocurrntMap이 쓰레드에서 더 빠른 처리속도를 보여준다");
    }

    @Test
    public void lazyMapGetInvalidKey() {
       Map lazyMap = mapService.getLazyMap();
       Object now =  lazyMap.get("INVALID_KEY");
       Assert.isTrue(now !=null, "유효하지 않은 키값 입력");
    }

    @Test
    public void lazyMapPutNullKey(){
        Map lazyMap = mapService.getLazyMap();
        Object nullObj = null;
        lazyMap.put(null,lazyMap.get("invalid"));
    }

    @Test
    public void lazyMapPutNullValue(){
        Map lazyMap = mapService.getLazyMap();
        Object nullValue = null;
        lazyMap.put("test",nullValue);
    }


}
