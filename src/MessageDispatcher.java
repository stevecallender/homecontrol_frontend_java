import java.util.concurrent.TimeUnit;

import org.zeromq.ZMQ;

public class MessageDispatcher {
	
	private ZMQ.Socket publish;
	
	public MessageDispatcher (){
		
		ZMQ.Context context = ZMQ.context(1);		
		publish = context.socket(ZMQ.PUB);
		publish.bind("tcp://*:5555");
		
	}
	
	public void playDispatch(){
		publish.send("play");
	}
	public void pauseDispatch(){
		publish.send("pause");
	}
	public void nextDispatch(){
		publish.send("next");
	}
	public void prevDispatch(){
		publish.send("prev");
	}
	public void lightsOnDispatch(){
		publish.send("lightsOn");
	}
	public void lightsOffDispatch(){
		publish.send("lightsOff");
	} 
	
	
}
