package ExecutorTest;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import sun.net.www.http.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by huishen on 17/2/10.
 *
 */
public class ThreadPoolTaskExecutorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setQueueCapacity(10000);
        poolTaskExecutor.setCorePoolSize(50);
        poolTaskExecutor.setMaxPoolSize(100);
        poolTaskExecutor.setKeepAliveSeconds(5000);
        poolTaskExecutor.initialize();

        for(int i = 0; i < 100; i++){
            FutureTask<String> task = new FutureTask<>(new TaskWithResult(i));
            poolTaskExecutor.submit(task);
            String res = task.get();
            System.out.println(res);
        }

    }

    private static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(20);
    // TODO http client pool // private static //


    public static List<Bean> xxx(String term, int count){

        List<Bean> retList = new ArrayList<>(count);


        List<Future<Bean>> futures = new ArrayList<>(count);
        for(int i = 0;i<count;i++){
            futures.add(THREAD_POOL.submit(()->{
                HttpClient hc = null;// get from pool
                // TODO request and get response
                // parse response
                // return bean
                return null;

            }));
        }


        // iterite futures to get results
        // TODO
        // set retList


        return retList;
    }

    public static class Bean{
        private String id;
        private String name;
    }


}
