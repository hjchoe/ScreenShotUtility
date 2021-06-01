package screenshotutil;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class selectScreenshot
{
	JPanel shadow = new JPanel();
	JLabel selection = new JLabel();
	
    public selectScreenshot()
    {
    	selection.setOpaque(true);
    	selection.setBackground(new Color(255, 255, 255, 75));
    	selection.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    	
    	shadow.setOpaque(true);
    	shadow.setBackground(new Color(0, 0, 0, 25));
        shadow.setSize((int) Main.getFrame().d.getWidth(), (int) Main.getFrame().d.getHeight());
        shadow.setLocation(0, 0);

    	MouseSense ma = new MouseSense();
    	shadow.addMouseMotionListener(ma);
    	shadow.addMouseListener(ma);

    	Main.getFrame().add(shadow);
    	Main.getFrame().add(selection);
    	Main.getFrame().refresh();
    }
    
    class MouseSense extends MouseAdapter
    {
        public int startx;
        public int starty;
        public int currentx;
        public int currenty;
        public int endx;
        public int endy;

        @Override
        public void mouseReleased(MouseEvent e)
        {
        	this.endx = e.getX();
        	this.endy = e.getY();
        	
        	Main.getFrame().setVisible(false);
        	BufferedImage image = Main.getFrame().r.createScreenCapture(new Rectangle(Math.min(startx, currentx), Math.min(starty, currenty), Math.abs(startx-currentx), Math.abs(starty-currenty)));
        	
        	Main.getFrame().remove(shadow);
        	Main.getFrame().remove(selection);
        	Main.getFrame().refresh();
        	
        	Main.getFrame().setVisible(true);
        	
        	Main.getFrame().previewSave(image);
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
        	this.startx = e.getX();
        	this.starty = e.getY();
        	selection.setLocation(startx, starty);
        	Main.getFrame().refresh();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
        	this.currentx = e.getX();
        	this.currenty = e.getY();
        	selection.setLocation(Math.min(startx, currentx), Math.min(starty, currenty));
        	selection.setSize(Math.abs(startx-currentx), Math.abs(starty-currenty));
        	Main.getFrame().refresh();
        }
    }
}
