import java.awt.EventQueue;

public class Launcher {
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopLevelContainer frame = new TopLevelContainer();
					frame.setVisible(true);
					frame.setMenuBar(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
