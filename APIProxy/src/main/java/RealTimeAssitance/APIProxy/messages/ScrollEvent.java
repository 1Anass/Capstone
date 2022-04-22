package RealTimeAssitance.APIProxy.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ScrollEvent {
    
    private String eventType;
    private String path;
    private String scrollHeight;
    private String userId;

    
    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        String scrollJSON = "not yet converted to string";
        try {
            scrollJSON = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return scrollJSON;
    }
    

}
