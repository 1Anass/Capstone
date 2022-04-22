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
public class ClickEvent {

    private String eventType;
    private String path;
    private String userId;
    private String tagName;
    private String targetId;
    private String targetClass;
    private float pageX;
    private float pageY;

    

    public float getPageX() {
        return pageX;
    }



    public String getPath() {
        return path;
    }



    public void setPath(String path) {
        this.path = path;
    }



    public void setPageX(float pageX) {
        this.pageX = pageX;
    }



    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        String clickJSON = "not yet converted to string";
        try {
            clickJSON = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return clickJSON;
    }

}
