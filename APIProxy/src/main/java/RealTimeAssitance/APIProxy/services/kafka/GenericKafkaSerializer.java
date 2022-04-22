package RealTimeAssitance.APIProxy.services.kafka;

import java.io.IOException;
import org.apache.kafka.common.serialization.Serializer;

import RealTimeAssitance.APIProxy.util.JsonSerdes;

public class GenericKafkaSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return JsonSerdes.getSingleton().serialize(data);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
    
}
