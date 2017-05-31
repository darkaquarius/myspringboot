package boot.logback;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Created by huishen on 16/10/18.
 */
public class LogbackDemo {
    private static Logger log = LoggerFactory.getLogger(LogbackDemo.class);

    public static void main(String args[]){
        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");
    }

}
