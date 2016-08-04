import java.util.concurrent.TimeUnit;

import org.zeromq.ZMQ;

public class MessageDispatcher {
	
	private ZMQ.Socket publish;
	
	public MessageDispatcher (){
		
		ZMQ.Context context = ZMQ.context(1);
		
		String portList[] = {"5560","5561","5562","5563","5564","5565"};
		boolean connected = false;
		int portIndex = 0;
		publish = context.socket(ZMQ.PUB);
		while (!connected)
		{
			String port = portList[portIndex];
			try {
				publish.bind("tcp://*:"+port);
				connected = true;
				System.out.println("Connected to port: "+port);
			}
			catch(Exception e)
			{
				System.out.println("Trying next port..");
				portIndex++;
			}
			
		}
		
		
		
		
		
		
	}
	
	public void playDispatch(){
		publish.sendMore("MediaCommand");
		publish.send("play");
	}
	public void pauseDispatch(){
		publish.sendMore("MediaCommand");
		publish.send("pause");
	}
	public void nextDispatch(){
		publish.sendMore("MediaCommand");
		publish.send("next");
	}
	public void prevDispatch(){
		publish.sendMore("MediaCommand");
		publish.send("prev");
	}
	public void lightsOnDispatch(){
		publish.sendMore("LightsCommand");
		publish.send("lightsOn");
	}
	public void lightsOffDispatch(){
		publish.sendMore("LightsCommand");
		publish.send("lightsOff");
	} 
	
	
}
