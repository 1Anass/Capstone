package RealTimeAssitance.APIProxy.util;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Event<U> {

    private final String userId;
	private final long timestamp;
	private final U payload;

	public Event(U payload, String id) {
		this.userId = id;
		this.timestamp = new Date().getTime();
		this.payload = payload;
	}

    
}
