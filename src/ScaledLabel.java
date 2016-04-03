import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import HelperUtilities.ImageDrawer;

public class ScaledLabel extends JLabel {

	private String iconFilename;
	private String selectedIconFilename;

	public ScaledLabel(String iconFilename, String selectedIconFilename){
		super();
		setIcon(new ImageIcon(TopLevelContainer.class.getResource(iconFilename)));
		this.iconFilename = iconFilename;
		this.selectedIconFilename = selectedIconFilename;

	}
	
	public void setIcon(String iconFilename, String selectedIconFilename)
	{
		setIcon(new ImageIcon(TopLevelContainer.class.getResource(iconFilename)));
		this.iconFilename = iconFilename;
		this.selectedIconFilename = selectedIconFilename;
		paint(getGraphics());
	}

	protected void paintComponent(Graphics g) {
		ImageIcon icon = (ImageIcon) getIcon();
		if (icon != null) {
			ImageDrawer.drawScaledImage(icon.getImage(), this, g);
		}
	}

	

}


