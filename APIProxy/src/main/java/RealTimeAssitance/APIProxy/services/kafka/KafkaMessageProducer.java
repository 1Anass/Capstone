package RealTimeAssitance.APIProxy.services.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import RealTimeAssitance.APIProxy.util.Event;

@Component
public class KafkaMessageProducer {

    // private KafkaProducer<T, Event<U>> messageProducer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String brokers;

    private String topicName = "userevents";

    public KafkaMessageProducer() {
       
    }

    

    public String send(String message) {

        Date date = new Date();

        ProducerRecord<String, String> record = new ProducerRecord<String,String>(topicName, getKey(message), message);
        
        
        kafkaTemplate.send(record);

        return "Message pushed to kafka: \n " + record.toString();
    }

    protected int partitionHash(String message){

        int hashCode = 0;

        StringTokenizer defaultTokenizer = new StringTokenizer(message, "\"");

        int count = 0;
        while (defaultTokenizer.hasMoreTokens() && count < 8) {
            count = count + 1;
        }

        String path = defaultTokenizer.nextToken();

        HashCodeBuilder hasher = new HashCodeBuilder();

        hasher.append(path);

        hashCode = hasher.toHashCode();


        return hashCode;


    }

    protected String getKey(String message){

        StringTokenizer defaultTokenizer = new StringTokenizer(message, "\"");

        int count = 0;
        while (defaultTokenizer.hasMoreTokens() && count < 11) {
            defaultTokenizer.nextToken();
            count = count + 1;
        }

        String userId = defaultTokenizer.nextToken();

        System.out.println("\n\n -------------------- " + userId + "--------------------------\n\n");
        
        return userId;


    }



}
