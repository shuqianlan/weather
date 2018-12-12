package com.ilifesmart.javaprogect;

import com.ilifesmart.javaprogect.ExectorTestDrive.WithInThreadExecutor;
import com.ilifesmart.javaprogect.cache.Computable;
import com.ilifesmart.javaprogect.cache.Memoizer4;
import com.ilifesmart.javaprogect.entry.BoundedHashSet;
import com.ilifesmart.javaprogect.entry.CountDownLatchTestDrive;
import com.ilifesmart.javaprogect.entry.FutureTaskTestDrive;
import com.ilifesmart.javaprogect.entry.HidderIterator;
import com.ilifesmart.javaprogect.entry.MOBaseThreadSafe;
import com.ilifesmart.javaprogect.entry.WorkerRunnable;
import com.ilifesmart.javaprogect.nature_module_models.Ep;
import com.ilifesmart.javaprogect.nature_module_models.Momgr;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.*;
import static java.lang.Thread.sleep;

public class MyClass {

    private final AtomicInteger step = new AtomicInteger(0);
    private ConcurrentHashMap<String, Point> locations = new ConcurrentHashMap<>();
    private List<String> strs = new CopyOnWriteArrayList<>();

    public Map<String, Point> getLocations() {
        return Collections.unmodifiableMap(locations);
    }

    public void setLocation(String key, Point point) {
        locations.replace(key, point);
    }

    public Point getLocation(String key) {
        return locations.get(key);
    }

    public static void main(String[] args) {
        System.out.println("Java test -------------------------- new Date " + System.currentTimeMillis()/1000);

//        onCountDownLatchTestDrive(); //闭锁同步测试.
//        onFutureTaskTestDrive();     //FutureTask测试.
//        onBoundedHashSetTestDrive(); //限定界限的set.
//        onBlockHashMapTestDrive();   //缓存数据的并发性存储.
//        onAnalogModels();            //测试数据，确定.synchronized和ConcurrentHashMap的区别
        onExecutorTestDrive();
    }

    private static void onCountDownLatchTestDrive() {
        long offsetDelayNanos = CountDownLatchTestDrive.timeTasks(6, new Runnable() {
            @Override
            public void run() {
                System.out.println("BBBBBB ");
            }
        });

        System.out.println("cost " + offsetDelayNanos + " (nanos)");
    }

    private static void onFutureTaskTestDrive() {
        FutureTaskTestDrive task = new FutureTaskTestDrive();
        System.out.println("Java test -------------------------- new Date " + System.currentTimeMillis()/1000);
        try {
            MOBaseThreadSafe safe = task.get();
            System.out.println("safe -- " + safe);
        } catch(Exception ex) {
        	ex.printStackTrace();
        } finally {
            System.out.println("Java test -------------------------- new Date " + System.currentTimeMillis()/1000);
        }
    }

    private static void onBoundedHashSetTestDrive() {
        BoundedHashSet<String> strs = new BoundedHashSet<>(6);

        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("Added " + (strs.add("item-" + (i+1))));
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
    }

    private static void onBlockHashMapTestDrive() {
        Memoizer4<String, Integer> map = new Memoizer4<>(new Computable() {
            @Override
            public Object compute(Object arg) throws InterruptedException {
                try {
                    sleep(3000);
                } catch(Exception ex) {
                	ex.printStackTrace();
                }
                if (arg instanceof Integer) {
                    return (Integer)arg;
                } else {
                    return new Random().nextInt();
                }
            }
        });

        for (int i = 0; i < 6; i++) {
            new Thread(new WorkerRunnable("item-"+i, map, "Set&Cache")).start();
        }

        for (int i = 0; i < 6; i++) {
            new Thread(new WorkerRunnable("item-"+i, map, "Get")).start();
        }
    }

    private static void onAnalogModels() {
      Momgr instance = Momgr.getInstance();
      final String prePath = "lm/m2m/7504552/ep/xxxxx/2d3";
      AtomicInteger step = new AtomicInteger(1);
			CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    instance.putEp((Ep) new Ep().setName("name-"+step.get()).setId(prePath+step.getAndIncrement()));
                    latch.countDown();
                }
            }).start();
        }

      // 初始化结束后执行打印.
			try {
				latch.await();
			} catch(Exception ex) {
				ex.printStackTrace();
			}

			for (int i = 0; i < 10; i++) {
        Ep ep = instance.getEp(prePath+i);
        if (ep != null) {
            System.out.println("Index:"+i+" = " + ep.toString());
        }
			}
    }

    private static void onExecutorTestDrive() {
        // 单线程执行.
        List<WithInThreadExecutor> lists = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            lists.add(new WithInThreadExecutor(new Runnable() {
                @Override
                public void run() {
                    System.out.println("------- " + atomicInteger.getAndIncrement());
                }
            }));
        }

        for (WithInThreadExecutor executor:lists) {
            executor.execute();
        }

        // 线程池
        Executors.newFixedThreadPool(100); //创建固定数量的线程池.
    }
}
