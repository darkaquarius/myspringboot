package boot.service.impl;

import boot.dto.Greeting;
import boot.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by huishen on 17/12/17.
 *
 */

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate messaging;

    @Override
    public void autoSend() {
        // IntStream.of(10).forEach(i -> {
        //     messaging.convertAndSend("/topic/auto-send-res", new Greeting("hello auto-send"));
        // });
        for (int i = 0; i < 10; i++) {
            messaging.convertAndSend("/topic/auto-send-res", new Greeting("hello auto-send"));
        }
    }

}
