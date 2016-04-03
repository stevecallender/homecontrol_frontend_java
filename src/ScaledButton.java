import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import HelperUtilities.ImageDrawer;

public class ScaledButton extends JButton implements MouseListener {

	private String iconFilename;
	private String selectedIconFilename;

	public ScaledButton(String iconFilename, String selectedIconFilename){
		super();
		setIcon(new ImageIcon(TopLevelContainer.class.getResource(iconFilename)));
		this.iconFilename = iconFilename;
		this.selectedIconFilename = selectedIconFilename;
		this.addMouseListener(this);

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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.setIcon(new ImageIcon(TopLevelContainer.class.getResource(this.selectedIconFilename)));	
		paint(getGraphics());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.setIcon(new ImageIcon(TopLevelContainer.class.getResource(this.iconFilename)));
		paint(getGraphics());

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



}


