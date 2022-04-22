package RealTimeAssitance;

import java.io.IOException;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import RealTimeAssitance.StreamProcessing.Pipeline;

@SpringBootApplication
public class ApiProxyApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiProxyApplication.class, args);

		Pipeline pipeline = new Pipeline();
		StreamsBuilder streamsBuilder = new StreamsBuilder();
		pipeline.buildPipeline(streamsBuilder);

	}

}
