package RealTimeAssitance.APIProxy.controllers;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import RealTimeAssitance.APIProxy.messages.ClickEvent;
import RealTimeAssitance.APIProxy.messages.Message;
import RealTimeAssitance.APIProxy.services.ReadUserEventService;
import RealTimeAssitance.StreamProcessing.Pipeline;

@Controller
public class ReadUserEventController {

    @Autowired
    ReadUserEventService readUserEventService;

    @MessageMapping("/events")
    //@SendTo("/topic/assistance")
    @CrossOrigin()
    public String sendToKafka(@RequestBody String message) {


        System.out.println("Message received by controller: " + message);   


        String response = readUserEventService.pushToKafka(message);

   
        
        return response;

        }
        
        

    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public String handleException(Throwable exception) {
        return "server exception: " + exception.getMessage();
    }

}
