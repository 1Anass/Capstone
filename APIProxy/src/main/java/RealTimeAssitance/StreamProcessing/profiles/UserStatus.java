package RealTimeAssitance.StreamProcessing.profiles;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserStatus {

    // //private Profile1 profile1;
    // private String userId;
    private Profile2 profile2;
    private Profile3 profile3;
    private Profile1 profile1;
    private Profile4 profile4;
    // private Profile4 profile4;
    // private Profile5 profile5;


    public UserStatus(Profile2 p2, Profile3 p3, Profile1 p1, Profile4 p4){
        // this.userId = id;
        this.profile1 = p1;
        this.profile2 = p2;
        this.profile3 = p3;
        this.profile4 = p4;
    }

    

    public Profile4 getProfile4() {
        return profile4;
    }



    public void setProfile4(Profile4 profile4) {
        this.profile4 = profile4;
    }



    public Profile1 getProfile1() {
        return profile1;
    }



    public void setProfile1(Profile1 profile1) {
        this.profile1 = profile1;
    }



    public Profile2 getProfile2() {
        return profile2;
    }

    public void setProfile2(Profile2 profile2) {
        this.profile2 = profile2;
    }

    public Profile3 getProfile3() {
        return profile3;
    }

    public void setProfile3(Profile3 profile3) {
        this.profile3 = profile3;
    }

    public String inferMessage(){
        String message = "";

        if(this.profile1.getProbability() > 0.7){
            message = this.profile1.getResponseMessage();
            this.profile1.setProbability(0);
            this.profile1.setFlag(false);
            this.profile1.setTime(LocalDateTime.now());
            this.profile1.setCounter(0);

        }

        if(this.profile2.getProbability() > 0.7){
            message = this.profile2.getResponseMessage();
            this.profile2.setProbability(0);
            this.profile2.setFlag(false);
            this.profile2.setTime(LocalDateTime.now());

        }

        if(this.profile3.getProbability() > 0.7){
            message = this.profile3.getResponseMessage();
            this.profile3.setProbability(0);
            this.profile3.setFlag(false);
            this.profile3.setTime(LocalDateTime.now());

        }

        if(this.profile4.getProbability() > 0.7){
            message = this.profile4.getResponseMessage();
            this.profile4.setProbability(0);
            this.profile4.setFlag(false);
            this.profile4.setTime(LocalDateTime.now());

        }

        return message ;
    }

    

    
}
