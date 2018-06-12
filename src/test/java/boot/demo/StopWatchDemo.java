package boot.demo;

import boot.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Timed;
import org.springframework.util.StopWatch;

/**
 * @author huishen
 * @date 18/3/9 下午8:24
 */
public class StopWatchDemo extends BaseTest {

    /**
     * 监控方法执行时间
     */
    @Timed(millis = 500)
    @Repeat(2)
    // @Test(timeout = 100)
    @Test
    public void test() throws InterruptedException {
        StopWatch clock = new StopWatch();
        clock.start("TaskOneName");
        Thread.sleep(100 * 1);// 任务一模拟休眠3秒钟
        clock.stop();
        clock.start("TaskTwoName");
        Thread.sleep(100 * 3);// 任务一模拟休眠10秒钟
        clock.stop();
        clock.start("TaskThreeName");
        Thread.sleep(100 * 3);// 任务一模拟休眠10秒钟
        clock.stop();

        System.out.println(clock.prettyPrint());
        System.out.println("共耗费秒数=" + clock.getTotalTimeSeconds());
    }

}