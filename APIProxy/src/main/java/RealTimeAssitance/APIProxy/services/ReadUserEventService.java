package RealTimeAssitance.APIProxy.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RealTimeAssitance.APIProxy.messages.ClickEvent;
import RealTimeAssitance.APIProxy.messages.Message;
import RealTimeAssitance.APIProxy.messages.MouseoverEvent;
import RealTimeAssitance.APIProxy.messages.PageOpenEvent;
import RealTimeAssitance.APIProxy.messages.ScrollEvent;
import RealTimeAssitance.APIProxy.services.kafka.KafkaMessageProducer;
import RealTimeAssitance.APIProxy.util.Event;

@Service
public class ReadUserEventService {

    @Autowired
    private KafkaMessageProducer messageProducer;

    public String pushToKafka(String message){

        // check for type compatibility before pushing to kafka ?
         
         String response = messageProducer.send(message);
         System.out.println("Message pushed by service to kafka: " + message);   
         return response ;
    }

  

}
