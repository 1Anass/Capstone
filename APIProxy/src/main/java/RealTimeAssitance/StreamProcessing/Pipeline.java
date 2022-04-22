package RealTimeAssitance.StreamProcessing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import RealTimeAssitance.APIProxy.controllers.RealTimeAssistController;
import RealTimeAssitance.StreamProcessing.profiles.Profile1;
import RealTimeAssitance.StreamProcessing.profiles.Profile2;
import RealTimeAssitance.StreamProcessing.profiles.Profile3;
import RealTimeAssitance.StreamProcessing.profiles.Profile4;
import RealTimeAssitance.StreamProcessing.profiles.UserStatus;

@Component
public class Pipeline {

  private static final Serde<String> STRING_SERDE = Serdes.String();

  Map<String, UserStatus> users = new HashMap<>();

  public Pipeline() {
  }

  @Autowired
  RealTimeAssistController realTimeAssistController;

  @Autowired
  public void buildPipeline(StreamsBuilder streamsBuilder) {

    System.out.println("\n\nusers hashmap created: " + users.toString() + "  \n\n");

    KStream<String, String> messageStream = streamsBuilder
        .stream("userevents", Consumed.with(STRING_SERDE, STRING_SERDE));

    KTable<String, String> events = messageStream.mapValues(s -> {

      // extracting event as JSON object

      JsonMapper parser = new JsonMapper();
      JsonNode eventObject = null;
      try {
        eventObject = parser.readTree(s);
      } catch (JsonProcessingException e) {

        e.printStackTrace();

      }

      // extracting key

      String userKey = eventObject.findValuesAsText("userId").get(0);

      // string to print probas

      String userProba = "nothing yet";

      UserStatus userStatus = null;

      // if user generated events in the past, we use already declared profiles to
      // make the processing stateful
      if (users.containsKey(userKey)) {

        userStatus = users.get(userKey);

        // retrieving profiles from the hashmap !!add remainig profiles
        Profile2 p2 = userStatus.getProfile2();
        Profile3 p3 = userStatus.getProfile3();
        Profile1 p1 = userStatus.getProfile1();
        Profile4 p4 = userStatus.getProfile4();

        // check for profile 2 and 3 !! add remainig profile checking
        p2.isSimilarToEvent1(eventObject);
        p3.eventMatching(eventObject);
        p1.eventMatching(eventObject);
        p4.eventMatching(eventObject);

        userProba = "profile1: " + String.format("%.2f", p1.getProbability()) + " \nprofile2: "
            + String.format("%.2f", p2.getProbability()) + " \nprofile3: " + String.format("%.2f", p3.getProbability()) +
             " \nprofile4: " + String.format("%.2f", p4.getProbability())  + "\n";


        System.out.println("\n\n in if user proba: " + userProba + "  \n\n");

      } else {

        Profile1 p1 = new Profile1();
        Profile2 p2 = new Profile2();
        Profile3 p3 = new Profile3();
        Profile4 p4 = new Profile4();

        // check profile 1
        p1.eventMatching(eventObject);

        // check profile 3
        p3.eventMatching(eventObject);

        // check profile 4
        p4.eventMatching(eventObject);

        // check profile2
        p2.isSimilarToEvent1(eventObject);

        // create user status to be added to the hash map
        userStatus = new UserStatus(p2, p3, p1, p4);

        // userStatus added to hashMap
        users.put(userKey, userStatus);

        /* "profile2: " + String.format("%.2f", p2.getProbability()) + */
        userProba = "profile1: " + String.format("%.2f", p1.getProbability()) + " \nprofile2: "
            + String.format("%.2f", p2.getProbability()) + " \nprofile3: " + String.format("%.2f", p3.getProbability()) +
             " \nprofile4: " + String.format("%.2f", p4.getProbability())  + "\n";

        System.out.println("\n\n in else user proba: " + userProba + "  \n\n");

      }
      String message = " ";
      String chatmsg = userStatus.inferMessage();
       //+ userProba;

      if(!chatmsg.isBlank())
      {
         message = "Chatbot: " + chatmsg + "\n" ;
        realTimeAssistController.sendRealTimeResponse(message);
    }



      return message;

    })
        .toTable();

    events.toStream().to("messages");

  }

}
