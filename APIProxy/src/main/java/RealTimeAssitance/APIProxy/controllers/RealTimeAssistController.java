package RealTimeAssitance.APIProxy.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;


@Controller
public class RealTimeAssistController {

    //https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-message-flow

    private SimpMessagingTemplate template;

    @Autowired
    public RealTimeAssistController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RequestMapping(path="/topic/assistance", method=RequestMethod.POST)
    public void sendRealTimeResponse(String message) {
        String text = "[" + LocalDateTime.now() + "]:" + message;
        this.template.convertAndSend("/topic/assistance", text);
    }


    
    
}
