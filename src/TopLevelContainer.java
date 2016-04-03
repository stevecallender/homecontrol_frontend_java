import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.Box;

public class TopLevelContainer extends JFrame implements ActionListener {


	//TODO need to use swingworker class in order to monitor back end.
	//Inside the done method we can access GUI elements.
	//http://www.javaadvent.com/2012/12/multi-threading-in-java-swing-with-swingworker.html


	private JPanel contentPanel;

	//Buttons
	private JButton previousButton;
	private JButton nextButton;
	private JButton playButton;
	private JButton lightsButton;

	//Labels

	private JLabel timeLabel;
	private JLabel songLabel;
	private JLabel artistLabel;
	private JLabel tempLabel;
	private JLabel weatherLabel;
	private MessageDispatcher dispatch = new MessageDispatcher();
	
	//Frontend State
	private boolean isPlaying = false;
	private boolean lightsOn = false;
	
	private Component horizontalStrut_3;


	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public TopLevelContainer() throws IOException  {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(null);
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 10));

		JPanel informationPanel = new JPanel();
		informationPanel.setBackground(Color.BLACK);
		contentPanel.add(informationPanel, BorderLayout.NORTH);
		informationPanel.setLayout(new BorderLayout(0, 0));

		JPanel mediaPanel = new JPanel();
		mediaPanel.setBackground(Color.BLACK);
		informationPanel.add(mediaPanel, BorderLayout.WEST);
		mediaPanel.setLayout(new BorderLayout(0, 0));

		songLabel = new JLabel("song");
		songLabel.setForeground(Color.WHITE);
		songLabel.setBackground(Color.BLACK);
		songLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 22));
		mediaPanel.add(songLabel, BorderLayout.NORTH);

		artistLabel = new JLabel("artist");
		artistLabel.setBackground(Color.BLACK);
		artistLabel.setForeground(Color.WHITE);
		artistLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 22));
		mediaPanel.add(artistLabel, BorderLayout.SOUTH);

		JPanel timeTempLightsPanel = new JPanel();
		timeTempLightsPanel.setBackground(Color.BLACK);
		informationPanel.add(timeTempLightsPanel, BorderLayout.EAST);
		timeTempLightsPanel.setLayout(new GridLayout(0, 5, 0, 0));
		
		weatherLabel = new ScaledLabel("/Images/cloudWind.png","/Images/cloudWind.png");
		timeTempLightsPanel.add(weatherLabel);
		
		horizontalStrut_3 = Box.createHorizontalStrut(20);
		timeTempLightsPanel.add(horizontalStrut_3);

		tempLabel = new JLabel("14C");
		tempLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
		tempLabel.setForeground(Color.WHITE);
		timeTempLightsPanel.add(tempLabel);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		timeTempLightsPanel.add(horizontalStrut_2);


		timeLabel = new JLabel("time");
		timeTempLightsPanel.add(timeLabel);
		timeLabel.setBackground(Color.BLACK);
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));

		JPanel mediaButtonPanel = new JPanel();
		mediaButtonPanel.setBackground(Color.BLACK);
		contentPanel.add(mediaButtonPanel, BorderLayout.CENTER);
		mediaButtonPanel.setLayout(new GridLayout(2, 3, 0, 10));

		previousButton = new ScaledButton("/Images/previous.png","/Images/previousDown.png");
		mediaButtonPanel.add(previousButton);
		previousButton.addActionListener(this);
		previousButton.setActionCommand("prev");

		playButton = new ScaledButton("/Images/play.png","/Images/playDown.png");
		mediaButtonPanel.add(playButton);
		playButton.addActionListener(this);
		playButton.setActionCommand("playPause");

		nextButton = new ScaledButton("/Images/next.png","/Images/nextDown.png");
		mediaButtonPanel.add(nextButton);
		nextButton.addActionListener(this);
		nextButton.setActionCommand("next");

		Component horizontalStrut = Box.createHorizontalStrut(20);
		mediaButtonPanel.add(horizontalStrut);

		lightsButton = new ScaledButton("/Images/lightsoff.png","/Images/lightsoffDown.png");
		mediaButtonPanel.add(lightsButton);
		lightsButton.addActionListener(this);
		lightsButton.setActionCommand("lights");

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		mediaButtonPanel.add(horizontalStrut_1);

		// Start the back end handler
		MessageReceiver worker = new MessageReceiver(timeLabel, songLabel, artistLabel, tempLabel,weatherLabel);
		worker.execute();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equalsIgnoreCase("lights"))
		{
			if (this.lightsOn)
			{
				handleLightsOff();
				this.lightsOn = false;
			}
			else
			{
				handleLightsOn();
				this.lightsOn = true;
			}	
		}
		else if (command.equalsIgnoreCase("playPause"))
		{
			if (this.isPlaying)
			{
				handlePause();
				this.isPlaying = false;
			}
			else
			{
				handlePlay();
				this.isPlaying = true;
			}
		}
		
		else if (command.equalsIgnoreCase("next"))
		{
			handleNext();
		}
		
		else if (command.equalsIgnoreCase("prev"))
		{
			handlePrev();
		}

	}



	private void handleLightsOn(){
		//here we want to fire a message to the back end
		try {
			dispatch.lightsOnDispatch();
			ScaledButton tmp = (ScaledButton)lightsButton;

			//disable other buttons
			this.lightsButton.setEnabled(false);
			this.playButton.setEnabled(false);
			this.nextButton.setEnabled(false);
			this.previousButton.setEnabled(false);

			tmp.setIcon("/Images/lightsProgress1.png","/Images/lightsProgress1.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightsProgress2.png","/Images/lightsProgress2.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightsProgress3.png","/Images/lightsProgress3.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightsProgress4.png","/Images/lightsProgress4.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightsProgress5.png","/Images/lightsProgress5.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightsProgress6.png","/Images/lightsProgress6.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightsProgress7.png","/Images/lightsProgress7.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightsProgress8.png","/Images/lightsProgress8.png");
			Thread.sleep(50);
			tmp.setIcon("/Images/lightson.png","/Images/lightsonDown.png");

			this.lightsButton.setEnabled(true);
			this.playButton.setEnabled(true);
			this.nextButton.setEnabled(true);
			this.previousButton.setEnabled(true);

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void handleLightsOff(){
		try {
			dispatch.lightsOffDispatch();
			ScaledButton tmp = (ScaledButton)lightsButton;

			//disable other buttons
			this.lightsButton.setEnabled(false);
			this.playButton.setEnabled(false);
			this.nextButton.setEnabled(false);
			this.previousButton.setEnabled(false);

			//tmp.setIcon("/Images/lightson.png","/Images/lightsonDown.png");
			//Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress8.png","/Images/lightsProgress8.png");
//			Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress7.png","/Images/lightsProgress7.png");
//			Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress6.png","/Images/lightsProgress6.png");
//			Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress5.png","/Images/lightsProgress5.png");
//			Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress4.png","/Images/lightsProgress4.png");
//			Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress3.png","/Images/lightsProgress3.png");
//			Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress2.png","/Images/lightsProgress2.png");
//			Thread.sleep(50);
//			tmp.setIcon("/Images/lightsProgress1.png","/Images/lightsProgress1.png");
//			Thread.sleep(50);
			tmp.setIcon("/Images/lightsoff.png","/Images/lightsoffDown.png");
			Thread.sleep(50);

			this.lightsButton.setEnabled(true);
			this.playButton.setEnabled(true);
			this.nextButton.setEnabled(true);
			this.previousButton.setEnabled(true);

		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private void handlePlay(){
		dispatch.playDispatch();
		ScaledButton tmp = (ScaledButton)playButton;
		tmp.setIcon("/Images/play.png","/Images/playDown.png");
	}
	
	private void handlePause(){
		dispatch.pauseDispatch();
		ScaledButton tmp = (ScaledButton)playButton;
		tmp.setIcon("/Images/pause.png","/Images/pauseDown.png");
	}
	
	private void handleNext(){
		dispatch.nextDispatch();
	}

	private void handlePrev(){
		dispatch.prevDispatch();

	}
}
