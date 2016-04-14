import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class VideoPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage frame;
	
	public void paint(Graphics g) {
		g.drawImage(frame, 0, 0, this);
	}
	
	public void setFrame(BufferedImage frame) {
		this.frame = frame;
	}
}
