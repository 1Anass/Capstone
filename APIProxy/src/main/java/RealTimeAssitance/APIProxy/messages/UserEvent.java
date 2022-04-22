package RealTimeAssitance.APIProxy.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {

    private String path;
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
        return "path: " + this.path + " tagName: "+ this.tagName + " targetId: "+ this.targetId + " targetClass: "+ this.targetClass+
        " pageX: " + this.pageX + " pageY: "+ this.pageY;
    }

}
