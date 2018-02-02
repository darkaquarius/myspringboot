package boot.job;

import boot.dto.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by huishen on 17/12/18.
 *
 */

@Component
public class WebSocketJob {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketJob.class);

    @Autowired
    private SimpMessagingTemplate messaging;

    @Scheduled(fixedDelay = 1000)
    public void autoSendJob() {
        messaging.convertAndSend("/topic/auto-send-res", new Greeting("hello auto-send"));
    }

}
