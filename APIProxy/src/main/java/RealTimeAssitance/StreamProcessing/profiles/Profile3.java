package RealTimeAssitance.StreamProcessing.profiles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

/*

This class represents profile 3 that defines a user as planning to apply when these events happen:
event1:  visited a page of programs of study <==> eventType == pageOpen && path.contains("undergraduates/")
event2: visited tuition fees page <==> eventType == pageOpen && path.contains("tuition-fees")
event3: visited admission tests page <==> eventType == pageOpen && path.contains("tests")

probability that a user matches this profile is set to 18%
probability that events happen given a user who matches this profile are:
event1: 30%
event2: 30%
event3: 40%

*/
@Getter
@Setter

public class Profile3 {

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    // probability that a user matches this profile
    private double probability;

    private final double profileProbability = 0.18;
    private final double event1Probability = 0.05;
    private final double event2Probability = 0.1;
    private final double event3Probability = 0.15;

    private LocalDateTime time = LocalDateTime.now();
    private boolean flag;

    public String getResponseMessage() {
        return responseMessage;
    }

    // a user might generate an event of interest, but not necessarily match the
    // profiles that I am looking for.
    // so when computing the probability of an event, it will sum of (probabilities
    // of profiles having this event * probability of event happens given the user
    // matches the profile)
    // this eventProbaGivenRandomUser needs to figure in this sum
    private final double eventProbaGivenRandomUser = 0.10 * 0.10;

    // this list represents remaining events. Use case: if event 1 already happened,
    // and event 2 happens as well, we update probability now that both events
    // happen.
    // Each boolean value corresponds to an event
    private Map<Integer, Double> happeningEvents;

    private final String responseMessage = "Are you planning to apply? Apply now through this link: https://my.aui.ma/ICS/Online_Application_Form/";

    public Profile3() {
        this.probability = 0;
        this.happeningEvents = new HashMap<>();
        this.flag = true;
    }

    

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void eventMatching(JsonNode event) {

        Duration duration = Duration.between(this.time, LocalDateTime.now());
        if (flag == false && duration.toMinutes() < 2)
            return;

        else {

            this.flag = true;
            this.isSimilarToEvent1(event);
            this.isSimilarToEvent2(event);
            this.isSimilarToEvent3(event);
        }

    }

    // given an event, it checks whether it matches with event1
    public boolean isSimilarToEvent1(JsonNode event) {

        boolean result = false;
        String path = event.findValuesAsText("path").get(0);
        String eventType = event.findValuesAsText("eventType").get(0);

        if (path.contains("admissions.html") && eventType.equals("pageOpen")) {
            happeningEvents.put(1, event1Probability);
            this.updateProbability();
            result = true;
        }
        return result;
    }

    // given an event, it checks whether i2.6243999999999998E-5t matches with event2
    public boolean isSimilarToEvent2(JsonNode event) {
        double event1probability = 0.3;
        boolean result = false;
        String path = event.findValuesAsText("path").get(0);
        String eventType = event.findValuesAsText("eventType").get(0);

        if (path.contains("tuition-fees") && eventType.equals("pageOpen")) {
            happeningEvents.put(2, event2Probability);
            this.updateProbability();
            result = true;
        }
        return result;
    }

    // given an event, it checks whether it matches with event3
    public boolean isSimilarToEvent3(JsonNode event) {
        double event1probability = 0.4;
        boolean result = false;
        String path = event.findValuesAsText("path").get(0);
        String eventType = event.findValuesAsText("eventType").get(0);

        if (path.contains("tests") && eventType.equals("pageOpen")) {
            happeningEvents.put(3, event3Probability);
            this.updateProbability();
            result = true;
        }
        return result;
    }

    public void updateProbability() {

        // compute proba of events happening given a user who match profile 3
        Double probaEventsGivenProfile = (double) 1;

        Iterator iterator = this.happeningEvents.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            probaEventsGivenProfile = probaEventsGivenProfile
                    * ((Double) entry.getValue() * this.profileProbability + this.eventProbaGivenRandomUser);
        }

        iterator = this.happeningEvents.entrySet().iterator();

        Double probaEvents = (double) 1;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            probaEvents = probaEvents * ((Double) entry.getValue() * this.profileProbability);
        }

       
        this.probability = bayesFormulaCalculator(probaEventsGivenProfile, probaEvents);

        System.out.println("\n\n(" + Double.toString(probaEvents) + " * " + Double.toString(this.profileProbability) + ")/" + Double.toString(probaEventsGivenProfile) + " = " + Double.toString(this.probability) + "----------------\n\n");

    }

    public double bayesFormulaCalculator(double p3, double p1) {

        // p = (p1 * p2) / p3

        double result;

        result = (p1 * this.profileProbability) / p3;

        return 1 - (result / this.profileProbability);
    }

}
