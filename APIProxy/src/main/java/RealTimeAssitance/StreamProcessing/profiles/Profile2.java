package RealTimeAssitance.StreamProcessing.profiles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class Profile2 {

    private double probability;
    private final double profileProbability = 0.18;
    private double probaLessThan3 = 0.05;
    private double probaLessThan7 = 0.15;
    private double probaMoreThan7 = 0.20;
    private LocalDateTime time = LocalDateTime.now();
    private boolean flag;

    public String getResponseMessage() {
        return responseMessage;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    private final double eventProbaGivenRandomUser = 0.10 * 0.10;
    private Map<String, Integer> scrollsPerPage;

    private final String responseMessage = "If something is still unclear for you contact us via email admissions@aui.ma";
    // List<Boolean> remainingEvents;

    public Profile2() {
        this.flag = true;
        this.probability = 0;
        scrollsPerPage = new HashMap<>();

    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isSimilarToEvent1(JsonNode event) {
        boolean result = false;
        Duration duration = Duration.between(this.time, LocalDateTime.now());

        System.out.println("Time in p2: " + Long.toString(duration.toMinutes()) + "\n\n");

        // if flag is set to false then a user was already matched to this profile && if
        // it
        if (this.flag == false && duration.toMinutes() < 2)
            return false;
        else {
            this.flag = true;
            String eventType = event.findValuesAsText("eventType").get(0);
            int currentCounter = 0;
            if (eventType.equals("scroll")) {
                currentCounter = this.addEvent(event);
                if (currentCounter < 7) {
                    this.probability = bayesFormulaCalculator(probaLessThan3 * this.profileProbability,
                            (probaLessThan3 * this.profileProbability) + this.eventProbaGivenRandomUser);
                } else if (currentCounter < 10) {
                    this.probability = bayesFormulaCalculator(probaLessThan7 * this.profileProbability,
                            (probaLessThan7 * this.profileProbability) + this.eventProbaGivenRandomUser);
                } else {
                    this.probability = bayesFormulaCalculator(probaMoreThan7 * this.profileProbability,
                            (probaMoreThan7 * this.profileProbability) + this.eventProbaGivenRandomUser);
                    scrollsPerPage.put(event.findValuesAsText("path").get(0), 0);
                }

                result = true;
            }
        }
        return result;
    }

    public Map<String, Integer> getScrollsPerPage() {
        return scrollsPerPage;
    }

    public void setScrollsPerPage(Map<String, Integer> scrollsPerPage) {
        this.scrollsPerPage = scrollsPerPage;
    }

    public int addEvent(JsonNode event) {

        String path = event.findValuesAsText("path").get(0);
        if (scrollsPerPage.containsKey(path)) {
            scrollsPerPage.put(path, scrollsPerPage.get(path) + 1);
        } else {
            scrollsPerPage.put(path, 1);
        }

        int currentCounter = scrollsPerPage.get(path);

        return currentCounter;

    }

    public double bayesFormulaCalculator(double p1, double p3) {

        // p = (p1 * p2) / p3

        double result;

        result = (p1 * this.profileProbability) / p3;

        return result / this.profileProbability;
    }

}
