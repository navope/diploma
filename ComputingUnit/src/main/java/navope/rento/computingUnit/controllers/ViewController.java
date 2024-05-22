package navope.rento.computingUnit.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final SimpMessagingTemplate template;

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping(path = "/restart", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public void handleSse() throws IOException {
//        System.out.println(4);
//        template.convertAndSend("/topic/restart", "restart");
//    }
//    @EventListener(ContextRefreshedEvent.class)
//    public void contextRefreshed() throws IOException {
//        handleSse();
//    }
}

