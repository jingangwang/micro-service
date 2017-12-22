package com.wfj;

import java.util.concurrent.Callable;

/**
 * @author wjg
 * @date 2017/12/20 15:46
 */
public class Task implements Callable{
    @Override
    public Object call() throws Exception {
        Thread.sleep(10000);
        System.out.println("结束休眠");
        return "something";
    }
}
