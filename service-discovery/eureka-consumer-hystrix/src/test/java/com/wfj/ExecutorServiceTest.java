package com.wfj;

import java.util.concurrent.*;

/**
 * @author wjg
 * @date 2017/12/20 15:44
 */
public class ExecutorServiceTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService =
                new ThreadPoolExecutor(10,
                        10,
                        5000,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>(500),
                        r -> {
                            Thread thread = new Thread(r);
                            thread.setName("abc");
                            return thread;
                        }
                );
        Future<String> result = executorService.submit(new Task());
        //result.cancel(false);
        System.out.println("result:"+result.get());
    }
}

