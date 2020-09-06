package az.kapitalbank.websocketmock.config;

import az.kapitalbank.websocketmock.model.ProcessStatus;
import az.kapitalbank.websocketmock.model.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulingConfiguration {

    private final SimpMessagingTemplate template;

    public SchedulingConfiguration(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 3000)
    public void sendMsg() {

        Random random = new Random();

        HashMap<Integer,String> users = new HashMap<>();
        users.put(0,"latifovfm");
        users.put(1,"asadzadava");
        users.put(2,"basirovma");
        users.put(3,"sadiqovka");

        ProcessStatus[] statuses = ProcessStatus.values();

        WebSocketMessage msg = WebSocketMessage.builder()
                .user(users.get(random.nextInt(4)))
                .businessKey(UUID.randomUUID().toString())
                .status(statuses[random.nextInt(4)])
                .build();

        log.info(msg.toString());

        template.convertAndSend("/subscribe/"+msg.getUser(),msg);
    }

}
