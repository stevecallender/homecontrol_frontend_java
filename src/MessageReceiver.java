import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingWorker;
import org.zeromq.ZMQ;

public class MessageReceiver extends SwingWorker<Integer, String> {
	
	//http://www.javacreed.com/swing-worker-example/

	private JLabel timeLabel;
	private JLabel songLabel;
	private JLabel artistLabel;
	private JLabel tempLabel;
	private JLabel weatherLabel;
	private ZMQ.Context context;
	
	public MessageReceiver(JLabel timeLabel, JLabel songLabel, JLabel artistLabel, JLabel tempLabel, JLabel weatherLabel) {
		// TODO Auto-generated constructor stub
		this.timeLabel = timeLabel;
		this.songLabel = songLabel;
		this.artistLabel = artistLabel;
		this.tempLabel = tempLabel;
		this.weatherLabel = weatherLabel;
		context = ZMQ.context(1);
	}
	
	
	@Override
	protected Integer doInBackground() throws Exception {
		ZMQ.Socket subscribe = context.socket(ZMQ.SUB);
		subscribe.connect("tcp://192.168.1.1:5556");
		subscribe.subscribe("".getBytes());
        
		while (true){	
			String msg = new String(subscribe.recv());
			System.out.println("Pulling message: " + msg);
			publish(msg);
		}

	}
	
	  @Override
	  protected void process(final List<String> chunks) {
	    // Updates the messages text area
	    for (final String string : chunks) {
	      routeRequest(string);
	    }
	  }

	private void routeRequest(String request){
		
		char header = request.charAt(0);
		String payload = request.substring(1);
		
		switch (header){
		case ('1'):
			handleMediaUpdate(payload);
			break;
		case ('2'):
			handleLightsUpdate(payload);
			break;
		case ('3'):
			handlePlayingUpdate(payload);
			break;
		case ('4'):
			handleTimeUpdate(payload);
			break;
		case ('5'):
			handleTempUpdate(payload);
			break;
		case ('6'):
			handleWeatherUpdate(payload);
		default:
			System.out.println("Unrecognised header - disgarding message.");
		}
	}
	
	private void handleMediaUpdate(String payload){
		String trimmedPayload = payload.substring(0, payload.length());
		String[] splitString = trimmedPayload.split(" - ");
		String artist = splitString[0];
		String song = splitString[1];
		this.songLabel.setText(song);
		this.artistLabel.setText("by " + artist);
	}
	
	private void handleLightsUpdate(String payload){
		if (payload.equalsIgnoreCase("lightsOn"))
			return;
			//update the lights image & state
		else
			//update the lights image & state
			return;
	}
		
	private void handlePlayingUpdate(String payload)
	{
		if (payload.equalsIgnoreCase("play"))
			return;
			//update the playpause image & state
		else
			//update the playpause image & state
			return;
	}
	
	private void handleTimeUpdate(String payload){
		
		String[] splitTime = payload.split(":");
		String hours = splitTime[0];
		String mins = splitTime[1];
		if (mins.length() < 2)
			mins = "0" + mins;
		if (hours.length() < 2)
			hours = "0" + hours;
		this.timeLabel.setText(hours+":"+mins);
		
	}
	
	private void handleTempUpdate(String payload){
		this.tempLabel.setText(payload + "°c");
	}
	
	private void handleWeatherUpdate(String payload){
		ScaledLabel tmp = (ScaledLabel)weatherLabel;
		if (payload.startsWith("sun"))
			tmp.setIcon("/Images/sun.png","/Images/sun.png");
		else if (payload.startsWith("cloudWind"))
			tmp.setIcon("/Images/cloudWind.png","/Images/cloudWind.png");
		else if (payload.startsWith("rain"))
			tmp.setIcon("/Images/rain.png","/Images/rain.png");
		else if (payload.startsWith("wind"))
			tmp.setIcon("/Images/wind.png","/Images/wind.png");
		else if (payload.startsWith("lightening"))
			tmp.setIcon("/Images/lightening.png","/Images/lightening.png");
		else if (payload.startsWith("cloudSun"))
			tmp.setIcon("/Images/cloudSun.png","/Images/cloudSun.png");
		else if (payload.startsWith("cloudSnow"))
			tmp.setIcon("/Images/cloudSnow.png","/Images/cloudSnow.png");
		else
			System.out.println("Unrecognised weather - ignoring.");
		
	}
}
