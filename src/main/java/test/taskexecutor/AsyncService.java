package test.taskexecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/6/5.
 * 使用@Async注解是异步方法
 */
@Service
public class AsyncService {

    @Async
    public void executeAsyncTask(int i) {
        System.out.println("执行异步任务：" + i);
    }

    @Async
    public void executeAsyncTaskPlus(int i) {
        System.out.println("执行异步任务+1：" + (i + 1));
    }

}
