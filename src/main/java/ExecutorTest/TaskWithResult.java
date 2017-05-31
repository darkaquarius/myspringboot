package ExecutorTest;

import java.util.concurrent.Callable;

/**
 * Created by huishen on 17/2/10.
 */
public class TaskWithResult implements Callable<String> {

    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    public String call() throws Exception {
        System.out.println("call()方法被自动调用,干活！！！ " + Thread.currentThread().getName());
        //一个模拟耗时的操作
        for (int i = 999999; i > 0; i--);
        return"call()方法被自动调用，任务的结果是：" + id + " " + Thread.currentThread().getName();
    }

}
